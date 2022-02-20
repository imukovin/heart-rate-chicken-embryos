package com.imukstudio.heartrate.chicken.embryos.sdk.listeners

interface ListenersSDK {

    fun subscribeMeasureResult(callback: (pulse: Int, cycles: Int, time: Long) -> Unit)

    fun unsubscribeOnPulse()

    fun notifyMeasureResult(pulse: Int, cycles: Int, time: Long)
}
