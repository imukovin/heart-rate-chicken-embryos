package com.imukstudio.heartrate.chicken.embryos.sdk.store.impl

import com.imukstudio.heartrate.chicken.embryos.sdk.dto.MeasureData
import com.imukstudio.heartrate.chicken.embryos.sdk.store.MeasureStore
import java.util.Date
import kotlin.math.max

class MeasureStoreImpl: MeasureStore {
    private val measurements: MutableList<MeasureData<Int>> = mutableListOf()
    private var currentPulse: Int = 0
    private var passedTime: Float = 0F
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

    override fun setCurrentPulse(currentPulse: Int) {
        this.currentPulse = currentPulse
    }

    override fun setPassedTime(passedTime: Float) {
        this.passedTime = passedTime
    }

    override fun getCurrentPulse(): Int = currentPulse

    override fun getPassedTime(): Float = passedTime

    override fun getMeasurementValues(): List<Int> {
        val list = mutableListOf<Int>()
        measurements.forEach {
            list.add(it.measurement)
        }
        return list
    }

    override fun getMeasurementSize(): Int = measurements.size

    override fun clearStore() {
        measurements.clear()
        currentPulse = 0
        passedTime = 0F
        min = Int.MAX_VALUE
        max = Int.MIN_VALUE
    }

    companion object {
        private const val rollingAverageSize = 4
    }
}
