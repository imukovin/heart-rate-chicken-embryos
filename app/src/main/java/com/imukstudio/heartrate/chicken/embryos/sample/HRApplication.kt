package com.imukstudio.heartrate.chicken.embryos.sample

import android.app.Application
import com.imukstudio.heartrate.chicken.embryos.sdk.HRSdk
import com.imukstudio.heartrate.chicken.embryos.sdk.HRSdkBuilder

class HRApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        hrSdk = HRSdkBuilder().build(applicationContext = applicationContext)
    }

    companion object {
        lateinit var hrSdk: HRSdk
    }
}
