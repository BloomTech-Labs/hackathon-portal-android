package com.lambdaschool.hackathon_portal.di.app.modules

import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserAuth0
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import com.lambdaschool.hackathon_portal.repository.UserRepository
import com.lambdaschool.hackathon_portal.retrofit.HackathonApiInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Singleton
    @Provides
    @JvmStatic
    fun providesUserRepository(hackathonApiInterface: HackathonApiInterface,
                               userAuth0: UserAuth0,
                               user: User) =
        UserRepository(hackathonApiInterface, userAuth0, user)

    @Singleton
    @Provides
    @JvmStatic
    fun providesHackathonRepository(hackathonApiInterface: HackathonApiInterface,
                                    userRepository: UserRepository) =
        HackathonRepository(hackathonApiInterface, userRepository)
}