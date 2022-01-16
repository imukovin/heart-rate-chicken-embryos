package com.imukstudio.heartrate.chicken.embryos.sdk.interactor.impl

import android.app.Activity
import android.view.Surface
import android.view.TextureView
import com.imukstudio.heartrate.chicken.embryos.sdk.handler.MeasureHandler
import com.imukstudio.heartrate.chicken.embryos.sdk.interactor.MeasureInteractor
import com.imukstudio.heartrate.chicken.embryos.sdk.listeners.ListenersSDK

class MeasureInteractorImpl(
    private val measureHandler: MeasureHandler,
    private val listenersSDK: ListenersSDK
): MeasureInteractor {

    override fun startMeasure(activity: Activity, surfaceView: Surface, textureView: TextureView) {
        measureHandler.startMeasure(activity = activity, surfaceView = surfaceView, textureView = textureView)
    }

    override fun subscribeMeasureResult(callback: (pulse: Int) -> Unit) {
        listenersSDK.subscribeMeasureResult(callback)
    }

    override fun unsubscribeMeasureResult() {
        listenersSDK.unsubscribeOnPulse()
    }
}