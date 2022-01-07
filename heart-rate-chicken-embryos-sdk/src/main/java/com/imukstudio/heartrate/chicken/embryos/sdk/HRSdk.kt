package com.imukstudio.heartrate.chicken.embryos.sdk

import android.app.Activity
import android.util.Log
import android.view.Surface
import android.view.TextureView
import kotlinx.coroutines.*

object HRSdk {
//    private lateinit var cameraService: CameraService

    fun init(activity: Activity, surfaceView: Surface, textureView: TextureView) {
        Log.d("HRSdk", "init()")
        val cameraService = CameraService(activity, surfaceView)
        cameraService.start()
//        cameraService.stop()
        countRedPixels(textureView)
    }

    fun countRedPixels(textureView: TextureView) {
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                val currentBitmap = textureView.bitmap!!
                val pixelCount = currentBitmap.width * currentBitmap.height
                val pixels = IntArray(pixelCount)
                currentBitmap.getPixels(pixels, 0, textureView.width, 0, 0, textureView.width, textureView.height)
                var redPixelsCount = 0
                for (element in pixels) {
                    redPixelsCount += element shr 16 and 0xff
                }
                println("RedPixels: ${redPixelsCount}")
                delay(3_000)
            }
        }

    }
}
