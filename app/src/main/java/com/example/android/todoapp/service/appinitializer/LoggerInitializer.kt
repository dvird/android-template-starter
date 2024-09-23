package com.example.android.todoapp.service.appinitializer

import android.content.Context
import androidx.startup.Initializer
import com.example.android.todoapp.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree

// Initializes ExampleLogger.
class LoggerInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf()
    }
}