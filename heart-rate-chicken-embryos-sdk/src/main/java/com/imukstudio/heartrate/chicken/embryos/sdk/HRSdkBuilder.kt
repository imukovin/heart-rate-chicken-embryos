package com.imukstudio.heartrate.chicken.embryos.sdk

import android.content.Context
import io.realm.Realm

class HRSdkBuilder() {

    fun build(
        applicationContext: Context
    ): HRSdk {
        Realm.init(applicationContext)
        return HRSdk(
            applicationContext = applicationContext
        )
    }
}
