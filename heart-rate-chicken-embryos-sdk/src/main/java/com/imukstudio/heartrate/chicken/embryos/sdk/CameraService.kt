package com.imukstudio.heartrate.chicken.embryos.sdk

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.util.Log
import android.view.Surface
import androidx.core.app.ActivityCompat
import java.util.*

class CameraService(
    private val activity: Activity,
    private val surfaceView: Surface
) {
    private lateinit var cameraDevice: CameraDevice

    fun start() {
        Log.d(logTag, "CameraDevice start")
        val cameraManager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager

        val cameraId = cameraManager.cameraIdList[0]
        Log.d(logTag, "cameraId = $cameraId")

        val callback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
            override fun onOpened(camera: CameraDevice) {
                Log.d(logTag, "CameraDevice onOpened")
                cameraDevice = camera
                val stateCallback = object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        val captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                        captureRequestBuilder.addTarget(surfaceView)
                        captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH)

                        session.setRepeatingRequest(captureRequestBuilder.build(), null, null)
                    }

                    override fun onConfigureFailed(p0: CameraCaptureSession) {
                        Log.e(logTag, "Camera session configuration failed.")
                    }
                }
                cameraDevice.createCaptureSession(Collections.singletonList(surfaceView), stateCallback, null)
            }

            override fun onDisconnected(p0: CameraDevice) {
                Log.d(logTag, "CameraDevice onDisconnected")
            }

            override fun onError(p0: CameraDevice, p1: Int) {
                Log.d(logTag, "CameraDevice onError")
            }

        }

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.e(logTag, "No permission for CAMERA.")
            return
        }
        cameraManager.openCamera(cameraId, callback, null)
    }

    fun stop() {
        cameraDevice.close()
    }
}
