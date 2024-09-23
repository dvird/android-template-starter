

package com.example.android.todoapp

import android.app.Application
import androidx.startup.AppInitializer
import com.example.android.todoapp.service.appinitializer.LoggerInitializer
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Application that sets up Timber in the DEBUG BuildConfig.
 * Read Timber's documentation for production setups.
 */
@HiltAndroidApp
class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppInitializer.getInstance(applicationContext)
            .initializeComponent(LoggerInitializer::class.java)
            //.initializeComponent(LoggerInitializer::class.java)
            //.initializeComponent(LoggerInitializer::class.java)

    }
}
