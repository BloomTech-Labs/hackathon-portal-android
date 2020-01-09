package com.lambdaschool.hackathon_portal.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelsModule::class])
interface AppComponent {

    fun getActivityComponentBuilder(): ActivityComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindApplication(application: Application): Builder

        fun build(): AppComponent
    }
}