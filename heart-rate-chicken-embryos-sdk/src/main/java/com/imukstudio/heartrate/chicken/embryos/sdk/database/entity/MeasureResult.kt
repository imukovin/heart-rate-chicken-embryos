package com.imukstudio.heartrate.chicken.embryos.sdk.database.entity

import io.realm.RealmList
import io.realm.RealmObject
import java.util.UUID

open class MeasureResult() : RealmObject() {
    var id = UUID.randomUUID()
    var pulse: Int = 0
    var passedTime: Float = 0F
    var date: Long = 0
    var measureData: RealmList<Int> = RealmList()
}
