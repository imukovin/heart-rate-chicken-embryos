package com.imukstudio.heartrate.chicken.embryos.sdk.listeners

class ListenersSDKImpl: ListenersSDK {
    private var callbackMeasureResult: ((pulse: Int, cycles: Int, time: Long) -> Unit)? = null

    override fun subscribeMeasureResult(callback: (pulse: Int, cycles: Int, time: Long) -> Unit) {
        callbackMeasureResult = callback
    }

    override fun unsubscribeOnPulse() {
        callbackMeasureResult = null
    }

    override fun notifyMeasureResult(pulse: Int, cycles: Int, time: Long) {
        callbackMeasureResult?.invoke(pulse, cycles, time)
    }

}
