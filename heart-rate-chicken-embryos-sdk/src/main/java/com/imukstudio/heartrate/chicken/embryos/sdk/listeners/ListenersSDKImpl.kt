package com.imukstudio.heartrate.chicken.embryos.sdk.listeners

class ListenersSDKImpl: ListenersSDK {
    private var callbackMeasureResult: ((pulse: Int) -> Unit)? = null

    override fun subscribeMeasureResult(callback: (pulse: Int) -> Unit) {
        callbackMeasureResult = callback
    }

    override fun unsubscribeOnPulse() {
        callbackMeasureResult = null
    }

    override fun notifyMeasureResult(pulse: Int) {
        callbackMeasureResult?.invoke(pulse)
    }

}
