package com.husseinelfeky.tomasulosimulator

import android.app.Application
import timber.log.Timber

class TomasuloApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
