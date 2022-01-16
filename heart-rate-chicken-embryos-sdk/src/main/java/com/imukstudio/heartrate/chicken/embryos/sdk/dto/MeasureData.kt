package com.imukstudio.heartrate.chicken.embryos.sdk.dto

import java.util.Date

data class MeasureData<T>(
    val date: Date,
    val measurement: T
)
