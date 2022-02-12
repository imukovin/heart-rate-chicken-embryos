package com.imukstudio.heartrate.chicken.embryos.sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.imukstudio.heartrate.chicken.embryos.sample.HRApplication
import com.imukstudio.heartrate.chicken.embryos.sample.R

class JournalFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_journal, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HRApplication.hrSdk.measureInteractor.loadLastResults().forEach {
            println("--------> Id: ${it.id}; Pulse: ${it.pulse}")
        }
    }

}
