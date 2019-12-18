package com.lambdaschool.hackathon_portal.di

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
}