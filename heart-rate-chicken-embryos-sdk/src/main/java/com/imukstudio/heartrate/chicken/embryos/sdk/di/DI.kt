package com.imukstudio.heartrate.chicken.embryos.sdk.di

import org.kodein.di.DI

fun initDI(): DI =
    DI {
        import(listenersModule)
        import(interactorModule)
        import(measureModule)
    }
