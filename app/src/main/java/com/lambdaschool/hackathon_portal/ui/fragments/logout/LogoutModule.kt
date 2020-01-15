package com.lambdaschool.hackathon_portal.ui.fragments.logout

import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import com.lambdaschool.hackathon_portal.di.FragmentScope
import dagger.Module
import dagger.Provides

@Module
object LogoutModule {

    @FragmentScope
    @Provides
    @JvmStatic
    fun provideWebAuthProviderLogoutBuilder(auth0: Auth0): WebAuthProvider.LogoutBuilder =
        WebAuthProvider.logout(auth0)
            .withScheme("demo")
}