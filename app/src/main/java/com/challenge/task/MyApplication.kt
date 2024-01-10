package com.challenge.task

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
/**
 * Custom Application class annotated with @HiltAndroidApp for Dagger Hilt integration.
 * This class is responsible for initializing application-wide resources.
 */
@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}