package com.lambdaschool.hackathon_portal.di.app.modules

import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserAuth0
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import com.lambdaschool.hackathon_portal.retrofit.HackathonApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    private const val BASE_URL = "https://hackathon-portal.herokuapp.com/api/"

    @Singleton
    @Provides
    @JvmStatic
    fun provideRetrofitInstance(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @JvmStatic
    fun provideHackathonService(retrofit: Retrofit): HackathonApiInterface =
        retrofit.create(HackathonApiInterface::class.java)

    @Singleton
    @Provides
    @JvmStatic
    fun providesHackathonRepository(hackathonApiInterface: HackathonApiInterface,
                                    userAuth0: UserAuth0,
                                    user: User) =
        HackathonRepository(hackathonApiInterface, userAuth0, user)
}