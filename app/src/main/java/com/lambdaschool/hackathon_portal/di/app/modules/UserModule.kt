package com.lambdaschool.hackathon_portal.di.app.modules

import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserAuth0
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UserModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideUserAuth0(): UserAuth0 {
        return UserAuth0(-1, "", "")
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideUser(): User {
        return User(-1, null, null, "", "", mutableListOf())
    }
}