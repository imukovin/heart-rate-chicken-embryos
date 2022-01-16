package com.imukstudio.heartrate.chicken.embryos.sdk

import com.imukstudio.heartrate.chicken.embryos.sdk.di.initDI
import com.imukstudio.heartrate.chicken.embryos.sdk.interactor.MeasureInteractor
import org.kodein.di.instance

object HRSdk {
    private val kodein = initDI()

    val measureInteractor: MeasureInteractor by kodein.instance()
}
