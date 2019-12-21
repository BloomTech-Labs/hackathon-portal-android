package com.lambdaschool.hackathon_portal

import android.app.Application
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.lambdaschool.hackathon_portal.di.DaggerAppComponent

val prefs: Prefs by lazy {
    App.prefs
}

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .bindApplication(this)
            .build()
    }

    companion object {
        lateinit var prefs: Prefs
        lateinit var auth0: Auth0
        lateinit var credentialsManager: SecureCredentialsManager
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
        auth0 = Auth0(applicationContext)
        auth0.isOIDCConformant = true
        credentialsManager = SecureCredentialsManager(applicationContext, AuthenticationAPIClient(auth0), SharedPreferencesStorage(applicationContext))
    }
}