package com.imukstudio.heartrate.chicken.embryos.sdk.handler

import android.app.Activity
import android.view.Surface
import android.view.TextureView

interface MeasureHandler {

    fun startMeasure(activity: Activity, surfaceView: Surface, textureView: TextureView)

}