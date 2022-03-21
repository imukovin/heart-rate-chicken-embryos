package com.imukstudio.heartrate.chicken.embryos.sdk.interactor.impl

import com.imukstudio.heartrate.chicken.embryos.sdk.database.entity.MeasureResult
import com.imukstudio.heartrate.chicken.embryos.sdk.handler.MeasureHandler
import com.imukstudio.heartrate.chicken.embryos.sdk.interactor.JournalInteractor

class JournalInteractorImpl(
    private val measureHandler: MeasureHandler
): JournalInteractor {

    override fun deleteJournalElement(element: MeasureResult) {
        measureHandler.deleteMeasurementResultFromDB(element = element)
    }

}
