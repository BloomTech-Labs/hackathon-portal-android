package com.lambdaschool.hackathon_portal.di

import android.app.Activity
import android.app.Application
import com.lambdaschool.hackathon_portal.ui.fragments.dashboard.DashboardFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun getFragmentComponentBuilder(): FragmentComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindApplication(application: Application): Builder

        fun build(): AppComponent
    }

    fun injectMainActivity(activity: Activity)

    fun injectDashboardFragment(fragment: DashboardFragment)
}