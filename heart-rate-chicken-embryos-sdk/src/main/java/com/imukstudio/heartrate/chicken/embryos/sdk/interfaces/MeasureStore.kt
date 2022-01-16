package com.imukstudio.heartrate.chicken.embryos.sdk.interfaces

import com.imukstudio.heartrate.chicken.embryos.sdk.dto.MeasureData
import java.util.*

interface MeasureStore {

    fun add(measure: Int)

    fun stdValues(): List<MeasureData<Float>>

    fun getLastStdValues(count: Int): List<MeasureData<Int>>

    fun getLastTimestamp(): Date
}
