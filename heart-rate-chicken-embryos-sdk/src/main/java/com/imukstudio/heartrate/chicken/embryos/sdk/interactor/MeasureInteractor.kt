package com.imukstudio.heartrate.chicken.embryos.sdk.interactor

import android.app.Activity
import android.view.Surface
import android.view.TextureView

interface MeasureInteractor {

    fun startMeasure(activity: Activity, surfaceView: Surface, textureView: TextureView)

    fun subscribeMeasureResult(callback: (pulse: Int) -> Unit)

    fun unsubscribeMeasureResult()

}
