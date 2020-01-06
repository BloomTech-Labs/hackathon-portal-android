package com.lambdaschool.hackathon_portal.di

import com.auth0.android.provider.WebAuthProvider
import com.lambdaschool.hackathon_portal.App
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    private const val BASE_URL = "https://www.hackathon-portal.tech/"

    @Singleton
    @Provides
    @JvmStatic
    fun provideRetrofitInstance() =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @JvmStatic
    fun provideWebAuthProviderLogout() =
        WebAuthProvider.logout(App.auth0)
            .withScheme("demo")
}