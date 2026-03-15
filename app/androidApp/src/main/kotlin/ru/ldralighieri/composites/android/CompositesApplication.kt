package ru.ldralighieri.composites.android

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

internal class CompositesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setUpNightMode()
    }

    private fun setUpNightMode() {
        AppCompatDelegate.setDefaultNightMode(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            } else {
                AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            },
        )
    }
}