package com.imukstudio.heartrate.chicken.embryos.sdk

import com.imukstudio.heartrate.chicken.embryos.sdk.interfaces.MeasureStore
import com.imukstudio.heartrate.chicken.embryos.sdk.dto.MeasureData
import java.util.Date
import kotlin.math.max

class MeasureStoreImpl: MeasureStore {
    private val measurements: MutableList<MeasureData<Int>> = mutableListOf()
    private var min = Int.MAX_VALUE
    private var max = Int.MIN_VALUE

    override fun add(measure: Int) {
        measurements.add(
            MeasureData(
                date = Date(),
                measurement = measure
            )
        )
        if (measure < min) min = measure
        if (measure > max) max = measure
    }

    override fun stdValues(): List<MeasureData<Float>> {
        val stdValues: MutableList<MeasureData<Float>> = mutableListOf()
        measurements.forEachIndexed { index, measurement ->
            var sum = 0
            for (rollingAverageCounter in 0..rollingAverageSize) {
                sum += measurements[max(0, index - rollingAverageCounter)].measurement
            }
            stdValues.add(MeasureData(date = measurement.date, measurement = (sum / rollingAverageSize - min).toFloat() / (max - min)))
        }
        return stdValues
    }

    override fun getLastStdValues(count: Int): List<MeasureData<Int>> =
        if (count < measurements.size) {
            measurements.subList(measurements.size - 1 - count, measurements.size - 1)
        } else {
            measurements
        }

    override fun getLastTimestamp(): Date =
        measurements[measurements.size - 1].date

    companion object {
        private const val rollingAverageSize = 4
    }
}
