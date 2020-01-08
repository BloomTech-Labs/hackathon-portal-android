package com.lambdaschool.hackathon_portal.ui.fragments.login

import com.auth0.android.provider.WebAuthProvider
import com.lambdaschool.hackathon_portal.App
import com.lambdaschool.hackathon_portal.di.FragmentScope
import dagger.Module
import dagger.Provides

@Module
object LoginModule {

    @FragmentScope
    @Provides
    @JvmStatic
    fun provideWebAuthProviderLogin() =
        WebAuthProvider.login(App.auth0)
            .withScheme("demo")
            .withAudience("https://hackathon-portal.herokuapp.com/")
            .withScope("openid profile email offline_access")
}