package com.lambdaschool.hackathon_portal.fragments.login

import android.app.Application
import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import dagger.Module
import dagger.Provides

@Module
object LoginModule {

    const val auth0Domain = "hackathonportal.auth0.com"

    @Provides
    @JvmStatic
    fun provideWebAuthProviderLogin(application: Application) =
        WebAuthProvider.login(Auth0(application))
            .withScheme("demo")
            .withAudience(String.format("https://%s/userinfo", auth0Domain))
}