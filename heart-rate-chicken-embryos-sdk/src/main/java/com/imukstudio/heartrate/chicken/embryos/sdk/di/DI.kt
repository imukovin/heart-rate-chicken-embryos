package com.imukstudio.heartrate.chicken.embryos.sdk.di

import io.realm.Realm
import org.kodein.di.DI
import org.kodein.di.bindSingleton

fun initDI(realm: Realm): DI {
    val platform = DI.Module(name = "platform") {
        bindSingleton<Realm> { realm }
    }

    return DI {
        import(platform)
        import(listenersSDKModule)
        import(interactorModule)
        import(measureModule)
    }
}
