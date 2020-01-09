package com.lambdaschool.hackathon_portal.di

import android.app.Application
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.provider.WebAuthProvider
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
    fun provideAuth0(application: Application): Auth0 {
        val auth0 = Auth0(application.applicationContext)
        auth0.isOIDCConformant = true
        return auth0
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideSecureCredentialsManager(application: Application, auth0: Auth0) =
        SecureCredentialsManager(
            application.applicationContext,
            AuthenticationAPIClient(auth0),
            SharedPreferencesStorage(application.applicationContext))

    @Singleton
    @Provides
    @JvmStatic
    fun provideWebAuthProviderLogoutBuilder(auth0: Auth0): WebAuthProvider.LogoutBuilder =
        WebAuthProvider.logout(auth0)
            .withScheme("demo")

    @Singleton
    @Provides
    @JvmStatic
    fun providesHackathonRepository(hackathonApiInterface: HackathonApiInterface) =
        HackathonRepository(hackathonApiInterface)
}