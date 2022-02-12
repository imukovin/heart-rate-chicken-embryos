package com.imukstudio.heartrate.chicken.embryos.sample.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.imukstudio.heartrate.chicken.embryos.sample.HRApplication
import com.imukstudio.heartrate.chicken.embryos.sample.R

class MainFragment: Fragment() {
    private lateinit var textureView: TextureView
    private lateinit var pulseTextView: TextView
    private lateinit var cycleTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var startMeasurementBtn: Button

    override fun onStart() {
        super.onStart()

        HRApplication.hrSdk.measureInteractor.subscribeMeasureResult { pulse, cycle, time ->
            setMeasurementResultView(pulseValue = pulse, cycleValue = cycle, timeValue = time)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textureView = view.findViewById(R.id.surface)
        pulseTextView = view.findViewById(R.id.pulseTextView)
        cycleTextView = view.findViewById(R.id.cycleTextView)
        timeTextView = view.findViewById(R.id.secondTextView)
        startMeasurementBtn = view.findViewById(R.id.newMeasurementButton)

        startMeasurementBtn.setOnClickListener {
            HRApplication.hrSdk.measureInteractor.startMeasure(this.requireActivity(), Surface(textureView.surfaceTexture), textureView)
        }
    }

    override fun onStop() {
        super.onStop()
        HRApplication.hrSdk.measureInteractor.unsubscribeMeasureResult()
    }

    private fun setMeasurementResultView(pulseValue: Int, cycleValue: Int, timeValue: Float) {
        pulseTextView.text = "$pulseValue"
        cycleTextView.text = "$cycleValue"
        timeTextView.text = "$timeValue"
    }

}
