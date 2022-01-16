package com.imukstudio.heartrate.chicken.embryos.sdk.listeners

interface ListenersSDK {

    fun subscribeMeasureResult(callback: (pulse: Int) -> Unit)

    fun unsubscribeOnPulse()

    fun notifyMeasureResult(pulse: Int)
}
