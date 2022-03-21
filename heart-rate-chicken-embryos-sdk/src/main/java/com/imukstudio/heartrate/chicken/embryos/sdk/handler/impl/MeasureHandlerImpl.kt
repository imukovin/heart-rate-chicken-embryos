package com.imukstudio.heartrate.chicken.embryos.sdk.handler.impl

import android.annotation.SuppressLint
import android.app.Activity
import android.os.CountDownTimer
import android.view.Surface
import android.view.TextureView
import com.imukstudio.heartrate.chicken.embryos.sdk.CameraService
import com.imukstudio.heartrate.chicken.embryos.sdk.database.entity.MeasureResult
import com.imukstudio.heartrate.chicken.embryos.sdk.handler.MeasureHandler
import com.imukstudio.heartrate.chicken.embryos.sdk.listeners.ListenersSDK
import com.imukstudio.heartrate.chicken.embryos.sdk.store.MeasureStore
import com.imukstudio.heartrate.chicken.embryos.sdk.store.impl.MeasureStoreImpl
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.max


class MeasureHandlerImpl(
    private val listenersSDK: ListenersSDK,
    private val database: Realm
): MeasureHandler {
    private lateinit var cameraService: CameraService
    private var measureStore: MeasureStore = MeasureStoreImpl()
    private val valleys: MutableList<Long> = mutableListOf()
    private var detectedValleys: Int = 0
    private var ticksPassed = 0

    override fun startMeasure(activity: Activity, surfaceView: Surface, textureView: TextureView) {
        initCameraService(activity, surfaceView)
        measureStore.clearStore()
        valleys.clear()
        ticksPassed = 0
        detectedValleys = 0

        object : CountDownTimer(MEASUREMENT_LENGTH, MEASUREMENT_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                if (CLIP_LENGTH > (++ticksPassed * MEASUREMENT_INTERVAL)) return
                val currentBitmap = textureView.bitmap!!
                val pixelCount = currentBitmap.width * currentBitmap.height
                val pixels = IntArray(pixelCount)
                currentBitmap.getPixels(pixels, 0, textureView.width, 0, 0, textureView.width, textureView.height)
                var redPixelsCount = 0
                for (element in pixels) {
                    redPixelsCount += element shr 16 and 0xff
                }
                measureStore.add(redPixelsCount)

                if (detectValley()) {
                    detectedValleys++
                    valleys.add(measureStore.getLastTimestamp().time)

                    val currentPulse = if (valleys.size == 1) {
                        (60f * detectedValleys / max(1f, (MEASUREMENT_LENGTH - millisUntilFinished - CLIP_LENGTH) / 1000f))
                    } else {
                        (60f * (detectedValleys - 1) / max(1f, (valleys[valleys.size - 1] - valleys[0]) / 1000f))
                    }
                    val passedTime = 1f * (MEASUREMENT_LENGTH - millisUntilFinished - CLIP_LENGTH) / 1000f
                    listenersSDK.notifyMeasureResult(pulse = currentPulse.toInt(), cycles = detectedValleys, time = (MEASUREMENT_LENGTH - millisUntilFinished) / 1000)
                    measureStore.setPassedTime(passedTime = passedTime)
                    measureStore.setCurrentPulse(currentPulse = currentPulse.toInt())
                }
            }

            override fun onFinish() {
                saveMeasurementResultToDB(currentMeasureStore = measureStore)
                stopCameraService()
            }

        }.start()
    }

    override fun loadLastResults(): List<MeasureResult> =
        database.where(MeasureResult::class.java).findAll()

    private fun initCameraService(activity: Activity, surfaceView: Surface) {
        cameraService = CameraService(activity = activity, surfaceView = surfaceView)
        cameraService.start()
    }

    private fun stopCameraService() {
        cameraService.stop()
    }

    @SuppressLint("NewApi")
    private fun detectValley(): Boolean {
        val valleyDetectionWindowSize = 13
        val subList = measureStore.getLastStdValues(valleyDetectionWindowSize)
        if (subList.size < valleyDetectionWindowSize) {
            return false
        } else {
            val referenceValue = subList[ceil(valleyDetectionWindowSize / 2f).toInt()].measurement
            subList.forEach { measureData ->
                if (measureData.measurement < referenceValue) return false
            }
            return subList[ceil(valleyDetectionWindowSize / 2f).toInt()].measurement != subList[ceil(valleyDetectionWindowSize / 2f).toInt() - 1].measurement
        }
    }

    override fun deleteMeasurementResultFromDB(element: MeasureResult) {
        MainScope().launch {
            // Solve problem with run on ui thread
            val result: RealmResults<MeasureResult> = database.where(MeasureResult::class.java).equalTo("id", element.id).findAll()
            database.executeTransaction {
                result.deleteAllFromRealm()
            }
        }
    }

    private fun saveMeasurementResultToDB(currentMeasureStore: MeasureStore) {
        val measureResult = MeasureResult()
        measureResult.pulse = currentMeasureStore.getCurrentPulse()
        measureResult.passedTime = currentMeasureStore.getPassedTime()
        measureResult.date = System.currentTimeMillis()
        measureResult.measureData = castListToRealmList(currentMeasureStore.getMeasurementValues())
        database.executeTransactionAsync { transaction ->
            transaction.insert(measureResult)
        }
    }

    private fun castListToRealmList(arrayList: List<Int>): RealmList<Int> {
        val realmList = RealmList<Int>()
        arrayList.forEach {
            realmList.add(it)
        }
        return realmList
    }
    
    companion object {
        private const val MEASUREMENT_INTERVAL: Long = 50
        private const val MEASUREMENT_LENGTH: Long = 25050
        private const val CLIP_LENGTH = 3500
    }
}