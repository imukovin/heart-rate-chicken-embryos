package com.imukstudio.heartrate.chicken.embryos.sdk.handler

import android.app.Activity
import android.view.Surface
import android.view.TextureView
import com.imukstudio.heartrate.chicken.embryos.sdk.database.entity.MeasureResult

interface MeasureHandler {

    fun startMeasure(activity: Activity, surfaceView: Surface, textureView: TextureView)

    fun loadLastResults(): List<MeasureResult>

    fun deleteMeasurementResultFromDB(element: MeasureResult)

}
