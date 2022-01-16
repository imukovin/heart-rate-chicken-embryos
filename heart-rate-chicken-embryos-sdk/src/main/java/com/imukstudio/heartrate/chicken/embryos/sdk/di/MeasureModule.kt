package com.imukstudio.heartrate.chicken.embryos.sdk.di

import com.imukstudio.heartrate.chicken.embryos.sdk.handler.MeasureHandler
import com.imukstudio.heartrate.chicken.embryos.sdk.handler.impl.MeasureHandlerImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider

internal val measureModule = DI.Module(name = "measure") {
    bindProvider<MeasureHandler> { MeasureHandlerImpl() }
}
