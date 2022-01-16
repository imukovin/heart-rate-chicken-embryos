package com.imukstudio.heartrate.chicken.embryos.sdk.di

import org.kodein.di.DI

fun initDI(): DI =
    DI {
        import(listenersSDKModule)
        import(interactorModule)
        import(measureModule)
    }
