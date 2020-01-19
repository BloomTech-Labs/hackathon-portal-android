package com.lambdaschool.hackathon_portal.di.app.modules

import android.app.Application
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object Auth0Module {

    @Singleton
    @Provides
    @JvmStatic
    fun provideAuth0(application: Application): Auth0 {
        val auth0 = Auth0(application.applicationContext)
        auth0.isOIDCConformant = true
        return auth0
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideSecureCredentialsManager(application: Application, auth0: Auth0) =
        SecureCredentialsManager(
            application.applicationContext,
            AuthenticationAPIClient(auth0),
            SharedPreferencesStorage(application.applicationContext))
}