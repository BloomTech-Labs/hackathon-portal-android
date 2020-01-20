package com.lambdaschool.hackathon_portal.di.app

import android.app.Application
import com.lambdaschool.hackathon_portal.di.activity.ActivityComponent
import com.lambdaschool.hackathon_portal.di.app.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        Auth0Module::class,
        UserModule::class,
        RepositoryModule::class,
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