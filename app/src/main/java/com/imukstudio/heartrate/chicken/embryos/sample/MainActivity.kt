package com.imukstudio.heartrate.chicken.embryos.sample

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.imukstudio.heartrate.chicken.embryos.sdk.HRSdk

class MainActivity : AppCompatActivity() {
    private lateinit var textureView: TextureView
    private lateinit var pulseTextView: TextView
    private lateinit var cycleTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var startMeasurementBtn: Button

    override fun onResume() {
        super.onResume()
        HRSdk.measureInteractor.subscribeMeasureResult { pulse, cycle, time ->
            setMeasurementResultView(pulseValue = pulse, cycleValue = cycle, timeValue = time)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textureView = findViewById(R.id.surface)
        pulseTextView = findViewById(R.id.pulseTextView)
        cycleTextView = findViewById(R.id.cycleTextView)
        timeTextView = findViewById(R.id.secondTextView)
        startMeasurementBtn = findViewById(R.id.newMeasurementButton)

        requestPermissionForCamera()

        startMeasurementBtn.setOnClickListener {
            HRSdk.measureInteractor.startMeasure(this, Surface(textureView.surfaceTexture), textureView)
        }
    }

    override fun onPause() {
        super.onPause()
        HRSdk.measureInteractor.unsubscribeMeasureResult()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_CAMERA_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun requestPermissionForCamera() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA_PERMISSION)
    }

    private fun setMeasurementResultView(pulseValue: Int, cycleValue: Int, timeValue: Float) {
        pulseTextView.text = "$pulseValue"
        cycleTextView.text = "$cycleValue"
        timeTextView.text = "$timeValue"
    }

    companion object {
        const val REQUEST_CODE_CAMERA_PERMISSION = 101
    }
}