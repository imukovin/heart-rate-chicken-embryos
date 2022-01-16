package com.imukstudio.heartrate.chicken.embryos.sdk.interactor.impl

import android.app.Activity
import android.view.Surface
import android.view.TextureView
import com.imukstudio.heartrate.chicken.embryos.sdk.handler.MeasureHandler
import com.imukstudio.heartrate.chicken.embryos.sdk.interactor.MeasureInteractor

class MeasureInteractorImpl(
    private val measureHandler: MeasureHandler
): MeasureInteractor {

    override fun startMeasure(activity: Activity, surfaceView: Surface, textureView: TextureView) {
        measureHandler.startMeasure(activity = activity, surfaceView = surfaceView, textureView = textureView)
    }

    override fun subscribeMeasureResult() {
        TODO("Not yet implemented")
    }

    override fun unsubscribeMeasureResult() {
        TODO("Not yet implemented")
    }
}