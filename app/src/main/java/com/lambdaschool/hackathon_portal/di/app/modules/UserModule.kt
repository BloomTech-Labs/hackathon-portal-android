package com.lambdaschool.hackathon_portal.di.app.modules

import com.lambdaschool.hackathon_portal.model.UserD
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UserModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserAuth0(): UserD.Auth0 {
        return UserD.Auth0(-1, "", "")
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserData(): UserD.UserE {
        return UserD.UserE(-1, null, null, "", "", mutableListOf())
    }
}