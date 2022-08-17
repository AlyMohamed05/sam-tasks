package com.silverbullet.samtasks

import android.app.Application
import androidx.viewbinding.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SamTasksApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // TODO: remove the debug tree in release mode
        Timber.plant(Timber.DebugTree())
    }
}