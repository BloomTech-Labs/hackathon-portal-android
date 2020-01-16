package com.lambdaschool.hackathon_portal

import android.app.Application
import com.lambdaschool.hackathon_portal.di.app.DaggerAppComponent

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .bindApplication(this)
            .build()
    }
}