package com.lambdaschool.hackathon_portal

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.lambdaschool.hackathon_portal.di.DaggerAppComponent

val prefs: Prefs by lazy {
    App.prefs!!
}


class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .bindApplication(this)
            .build()
    }

    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()

        prefs = Prefs(applicationContext)
    }
}