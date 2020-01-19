package com.lambdaschool.hackathon_portal.di.app.modules

import com.lambdaschool.hackathon_portal.model.User
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UserModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserAuth0(): User.Auth0 {
        return User.Auth0(-1, "", "")
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserData(): User.GetUser {
        return User.GetUser(-1, null, null, "", "", mutableListOf())
    }
}