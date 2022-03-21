package com.imukstudio.heartrate.chicken.embryos.sdk

import android.content.Context
import com.imukstudio.heartrate.chicken.embryos.sdk.di.initDI
import com.imukstudio.heartrate.chicken.embryos.sdk.interactor.JournalInteractor
import com.imukstudio.heartrate.chicken.embryos.sdk.interactor.MeasureInteractor
import io.realm.Realm
import io.realm.RealmConfiguration
import org.kodein.di.instance

class HRSdk(
    private val applicationContext: Context
) {
    private val kodein = initDI(
        realm = configureRealm()
    )

    val measureInteractor: MeasureInteractor by kodein.instance()
    val journalInteractor: JournalInteractor by kodein.instance()

    private fun configureRealm(): Realm {
        val realmName = "HRSdkAppDB"
        val config = RealmConfiguration.Builder()
            .name(realmName)
            .allowWritesOnUiThread(true)
            .build()

        return Realm.getInstance(config)
    }
}
