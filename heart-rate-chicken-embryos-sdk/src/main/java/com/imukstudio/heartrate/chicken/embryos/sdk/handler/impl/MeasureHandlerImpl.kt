package com.imukstudio.heartrate.chicken.embryos.sdk.handler.impl

import android.annotation.SuppressLint
import android.app.Activity
import android.os.CountDownTimer
import android.view.Surface
import android.view.TextureView
import com.imukstudio.heartrate.chicken.embryos.sdk.CameraService
import com.imukstudio.heartrate.chicken.embryos.sdk.store.impl.MeasureStoreImpl
import com.imukstudio.heartrate.chicken.embryos.sdk.handler.MeasureHandler
import com.imukstudio.heartrate.chicken.embryos.sdk.store.MeasureStore
import com.imukstudio.heartrate.chicken.embryos.sdk.listeners.ListenersSDK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.max

class MeasureHandlerImpl(
    private val listenersSDK: ListenersSDK
): MeasureHandler {
    private lateinit var cameraService: CameraService
    private var measureStore: MeasureStore = MeasureStoreImpl()
    private val valleys: MutableList<Long> = mutableListOf()
    private var detectedValleys: Int = 0
    private var ticksPassed = 0

    override fun startMeasure(activity: Activity, surfaceView: Surface, textureView: TextureView) {
        initCameraService(activity, surfaceView)
        measureStore = MeasureStoreImpl()
        detectedValleys = 0

        object : CountDownTimer(MEASUREMENT_LENGTH, MEASUREMENT_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                if (CLIP_LENGTH > (++ticksPassed * MEASUREMENT_INTERVAL)) return

                CoroutineScope(Dispatchers.Main).launch {
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
                        listenersSDK.notifyMeasureResult(pulse = currentPulse.toInt(), cycles = detectedValleys, time = passedTime)
                        println("Pulse: $currentPulse Detected: $detectedValleys Time: $passedTime")
                    }
//            printRedPixels(measureStore.stdValues())
                }
            }

            override fun onFinish() {
                stopCameraService()
            }

        }.start()
    }

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
    
    companion object {
        private const val MEASUREMENT_INTERVAL: Long = 45
        private const val MEASUREMENT_LENGTH: Long = 15000
        private const val CLIP_LENGTH = 3500
    }
}