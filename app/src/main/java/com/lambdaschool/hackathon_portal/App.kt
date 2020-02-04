package com.lambdaschool.hackathon_portal

import android.app.Application
import com.lambdaschool.hackathon_portal.di.app.DaggerAppComponent
import timber.log.Timber

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .bindApplication(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}