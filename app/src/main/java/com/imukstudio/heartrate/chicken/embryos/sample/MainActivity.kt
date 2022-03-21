package com.imukstudio.heartrate.chicken.embryos.sample

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.material.navigation.NavigationBarView
import com.imukstudio.heartrate.chicken.embryos.sample.fragments.JournalFragment
import com.imukstudio.heartrate.chicken.embryos.sample.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: NavigationBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissionForCamera()
        supportFragmentManager.beginTransaction().add(R.id.mainFrame, MainFragment(), "mainFragment").commit()

        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener {  menuItem ->
            when (menuItem.itemId) {
                R.id.page1 -> supportFragmentManager.beginTransaction().replace(R.id.mainFrame, MainFragment(), "mainFragment").commit()
                R.id.page2 -> supportFragmentManager.beginTransaction().replace(R.id.mainFrame, JournalFragment.newInstance(), "journalFragment").commit()
            }
            return@setOnItemSelectedListener true
        }

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

    companion object {
        const val REQUEST_CODE_CAMERA_PERMISSION = 101
    }
}