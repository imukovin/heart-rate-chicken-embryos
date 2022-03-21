package com.imukstudio.heartrate.chicken.embryos.sample.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.imukstudio.heartrate.chicken.embryos.sample.R
import com.imukstudio.heartrate.chicken.embryos.sdk.database.entity.MeasureResult
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.ArrayList

class JournalRecyclerViewAdapter(
    private val measurements: List<MeasureResult>,
    private val onDeleteClickListener: OnDeleteClickListener
): RecyclerView.Adapter<JournalRecyclerViewAdapter.MyViewHolder>() {
    @SuppressLint("SimpleDateFormat")
    private val dateFormatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val date = itemView.findViewById<TextView>(R.id.cardViewDate)
        val pulse = itemView.findViewById<TextView>(R.id.cardViewPulse)
        val measureTime = itemView.findViewById<TextView>(R.id.cardViewMeasureTime)
        val lineChart = itemView.findViewById<LineChart>(R.id.cardViewLineChart)
        val delete = itemView.findViewById<ImageView>(R.id.deleteElementJournal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_journal, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.date.text = dateFormatter.format(Date(measurements[position].date))
        holder.pulse.text = measurements[position].pulse.toString()
        holder.measureTime.text = measurements[position].passedTime.toString()
        holder.delete.setOnClickListener {
            onDeleteClickListener.deleteIconClick(measurements[position])
        }

        val entries = ArrayList<Entry>()
        var a = 0
        measurements[position].measureData.forEach {
            entries.add(Entry(a.toFloat(), it.toFloat()))
            a++
        }
        initLineChart(holder.lineChart)
        val lineData = LineData(LineDataSet(entries, "My chart"))
        holder.lineChart.data = lineData
    }

    override fun getItemCount(): Int = measurements.size

    private fun initLineChart(lineChart: LineChart) {

//        hide grid lines
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        lineChart.axisRight.isEnabled = false

        //remove legend
        lineChart.legend.isEnabled = false


        //remove description label
        lineChart.description.isEnabled = false


        //add animation
        lineChart.animateX(1000, Easing.EaseInSine)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
//        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

    }
}

interface OnDeleteClickListener {
    fun deleteIconClick(element: MeasureResult)
}
