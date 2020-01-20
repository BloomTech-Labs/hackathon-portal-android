package com.lambdaschool.hackathon_portal.di.app.modules

import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserAuth0
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import com.lambdaschool.hackathon_portal.repository.RepositoryObjects
import com.lambdaschool.hackathon_portal.repository.TeamRepository
import com.lambdaschool.hackathon_portal.repository.UserRepository
import com.lambdaschool.hackathon_portal.retrofit.HackathonApiInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RepoObjectsModule::class])
object RepositoryModule {

    @Singleton
    @Provides
    @JvmStatic
    fun providesRepositoryObjects(userAuth0: UserAuth0,
                                  user: User) =
        RepositoryObjects(userAuth0, user)

    @Singleton
    @Provides
    @JvmStatic
    fun providesUserRepository(hackathonApiInterface: HackathonApiInterface,
                               repositoryObjects: RepositoryObjects) =
        UserRepository(hackathonApiInterface, repositoryObjects)

    @Singleton
    @Provides
    @JvmStatic
    fun providesHackathonRepository(hackathonApiInterface: HackathonApiInterface,
                                    repositoryObjects: RepositoryObjects) =
        HackathonRepository(hackathonApiInterface, repositoryObjects)

    @Singleton
    @Provides
    @JvmStatic
    fun providesTeamRepository(hackathonApiInterface: HackathonApiInterface,
                               repositoryObjects: RepositoryObjects) =
        TeamRepository(hackathonApiInterface, repositoryObjects)
}