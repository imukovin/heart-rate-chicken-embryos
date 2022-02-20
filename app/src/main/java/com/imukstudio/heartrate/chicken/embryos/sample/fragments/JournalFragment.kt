package com.imukstudio.heartrate.chicken.embryos.sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imukstudio.heartrate.chicken.embryos.sample.HRApplication
import com.imukstudio.heartrate.chicken.embryos.sample.R
import com.imukstudio.heartrate.chicken.embryos.sample.adapters.JournalRecyclerViewAdapter

class JournalFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_journal, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val list = view.findViewById<RecyclerView>(R.id.journalRecyclerView)
        list.layoutManager = LinearLayoutManager(this.requireContext())
        list.adapter = JournalRecyclerViewAdapter(HRApplication.hrSdk.measureInteractor.loadLastResults())
    }

}
