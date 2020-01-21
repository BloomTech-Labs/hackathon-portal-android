package com.lambdaschool.hackathon_portal.di.app

import android.app.Application
import com.lambdaschool.hackathon_portal.di.app.modules.AppModule
import com.lambdaschool.hackathon_portal.di.activity.ActivityComponent
import com.lambdaschool.hackathon_portal.di.app.modules.Auth0Module
import com.lambdaschool.hackathon_portal.di.app.modules.UserModule
import com.lambdaschool.hackathon_portal.di.app.modules.ViewModelsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        Auth0Module::class,
        UserModule::class,
        ViewModelsModule::class
    ])
interface AppComponent {

    fun getActivityComponentBuilder(): ActivityComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindApplication(application: Application): Builder

        fun build(): AppComponent
    }
}