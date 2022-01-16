package com.imukstudio.heartrate.chicken.embryos.sample

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import androidx.core.app.ActivityCompat
import com.imukstudio.heartrate.chicken.embryos.sdk.HRSdk

class MainActivity : AppCompatActivity() {
    private lateinit var textureView: TextureView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textureView = findViewById(R.id.surface)

        requestPermissionForCamera()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_CAMERA_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    HRSdk.measureInteractor.startMeasure(this, Surface(textureView.surfaceTexture), textureView)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun requestPermissionForCamera() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA_PERMISSION)
    }

    companion object {
        const val REQUEST_CODE_CAMERA_PERMISSION = 101
    }
}