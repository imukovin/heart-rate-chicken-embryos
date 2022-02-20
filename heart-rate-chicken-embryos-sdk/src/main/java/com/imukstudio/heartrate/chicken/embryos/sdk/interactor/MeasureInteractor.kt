package com.imukstudio.heartrate.chicken.embryos.sdk.interactor

import android.app.Activity
import android.view.Surface
import android.view.TextureView
import com.imukstudio.heartrate.chicken.embryos.sdk.database.entity.MeasureResult

interface MeasureInteractor {

    fun startMeasure(activity: Activity, surfaceView: Surface, textureView: TextureView)

    fun subscribeMeasureResult(callback: (pulse: Int, cycles: Int, time: Long) -> Unit)

    fun unsubscribeMeasureResult()

    fun loadLastResults(): List<MeasureResult>
}
