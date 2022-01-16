package com.imukstudio.heartrate.chicken.embryos.sdk.di

import com.imukstudio.heartrate.chicken.embryos.sdk.listeners.ListenersSDK
import com.imukstudio.heartrate.chicken.embryos.sdk.listeners.ListenersSDKImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton

internal val listenersSDKModule = DI.Module(name = "listeners") {
    bindSingleton<ListenersSDK> { ListenersSDKImpl() }
}
