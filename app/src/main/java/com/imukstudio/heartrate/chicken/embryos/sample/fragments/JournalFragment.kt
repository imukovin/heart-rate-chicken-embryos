package com.imukstudio.heartrate.chicken.embryos.sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imukstudio.heartrate.chicken.embryos.sample.HRApplication
import com.imukstudio.heartrate.chicken.embryos.sample.R
import com.imukstudio.heartrate.chicken.embryos.sample.adapters.JournalRecyclerViewAdapter
import com.imukstudio.heartrate.chicken.embryos.sample.adapters.OnDeleteClickListener
import com.imukstudio.heartrate.chicken.embryos.sdk.database.entity.MeasureResult

class JournalFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_journal, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.journalRecyclerView)
        init()
    }

    private fun init() {
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        refreshRecyclerView()
    }

    private fun refreshRecyclerView() {
        recyclerView.adapter = JournalRecyclerViewAdapter(
            measurements = HRApplication.hrSdk.measureInteractor.loadLastResults(),
            onDeleteClickListener = object : OnDeleteClickListener {
                override fun deleteIconClick(element: MeasureResult) {
                    HRApplication.hrSdk.journalInteractor.deleteJournalElement(element = element)
                    refreshRecyclerView()
                }
            }
        )
    }

    companion object {
        fun newInstance(): JournalFragment =
            JournalFragment()
    }

}
