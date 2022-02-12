package com.imukstudio.heartrate.chicken.embryos.sdk.database.entity

import com.imukstudio.heartrate.chicken.embryos.sdk.dto.MeasureData
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.time.LocalDate
import java.util.*

open class MeasureResult() : RealmObject() {
    var id = UUID.randomUUID()
    var pulse: Int = 0
    var passedTime: Float = 0F
    var date: Long = 0
//    var measureData: List<Int> = emptyList()
}
