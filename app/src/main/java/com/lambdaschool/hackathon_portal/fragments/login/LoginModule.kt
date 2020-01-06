package com.lambdaschool.hackathon_portal.fragments.login

import com.auth0.android.provider.WebAuthProvider
import com.lambdaschool.hackathon_portal.App
import dagger.Module
import dagger.Provides

@Module
object LoginModule {

    @Provides
    @JvmStatic
    fun provideWebAuthProviderLogin() =
        WebAuthProvider.login(App.auth0)
            .withScheme("demo")
            .withAudience("https://hackathon-portal.herokuapp.com/")
            .withScope("openid offline_access")
}