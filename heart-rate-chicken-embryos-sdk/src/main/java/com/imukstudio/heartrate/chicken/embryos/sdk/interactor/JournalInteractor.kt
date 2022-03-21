package com.imukstudio.heartrate.chicken.embryos.sdk.interactor

import com.imukstudio.heartrate.chicken.embryos.sdk.database.entity.MeasureResult

interface JournalInteractor {

    fun deleteJournalElement(element: MeasureResult)

}
