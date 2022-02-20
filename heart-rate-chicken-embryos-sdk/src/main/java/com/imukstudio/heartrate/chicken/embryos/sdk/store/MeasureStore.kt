package com.imukstudio.heartrate.chicken.embryos.sdk.store

import com.imukstudio.heartrate.chicken.embryos.sdk.dto.MeasureData
import java.util.*

interface MeasureStore {

    fun add(measure: Int)

    fun stdValues(): List<MeasureData<Float>>

    fun getLastStdValues(count: Int): List<MeasureData<Int>>

    fun getLastTimestamp(): Date

    fun setCurrentPulse(currentPulse: Int)

    fun setPassedTime(passedTime: Float)

    fun getCurrentPulse(): Int

    fun getPassedTime(): Float

    fun getMeasurementValues(): List<Int>

    fun getMeasurementSize(): Int

    fun clearStore()
}
