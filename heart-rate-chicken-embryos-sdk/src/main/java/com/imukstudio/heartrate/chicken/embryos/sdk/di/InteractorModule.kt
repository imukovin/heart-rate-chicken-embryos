package com.imukstudio.heartrate.chicken.embryos.sdk.di

import com.imukstudio.heartrate.chicken.embryos.sdk.interactor.MeasureInteractor
import com.imukstudio.heartrate.chicken.embryos.sdk.interactor.impl.MeasureInteractorImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val interactorModule = DI.Module(name = "interactor") {
    bindProvider<MeasureInteractor> { MeasureInteractorImpl(instance(), instance()) }
}