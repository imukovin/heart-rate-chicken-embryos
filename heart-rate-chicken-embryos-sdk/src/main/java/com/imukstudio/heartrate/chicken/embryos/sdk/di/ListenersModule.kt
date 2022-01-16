package com.imukstudio.heartrate.chicken.embryos.sdk.di

import com.imukstudio.heartrate.chicken.embryos.sdk.interactor.ListenersInteractor
import com.imukstudio.heartrate.chicken.embryos.sdk.interactor.impl.ListenersInteractorImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider

internal val listenersModule = DI.Module(name = "listeners") {
    bindProvider<ListenersInteractor> { ListenersInteractorImpl() }
}
