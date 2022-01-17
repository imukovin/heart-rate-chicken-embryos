package com.imukstudio.heartrate.chicken.embryos.sdk.listeners

class ListenersSDKImpl: ListenersSDK {
    private var callbackMeasureResult: ((pulse: Int, cycles: Int, time: Float) -> Unit)? = null

    override fun subscribeMeasureResult(callback: (pulse: Int, cycles: Int, time: Float) -> Unit) {
        callbackMeasureResult = callback
    }

    override fun unsubscribeOnPulse() {
        callbackMeasureResult = null
    }

    override fun notifyMeasureResult(pulse: Int, cycles: Int, time: Float) {
        callbackMeasureResult?.invoke(pulse, cycles, time)
    }

}
