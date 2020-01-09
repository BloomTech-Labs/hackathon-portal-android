package com.lambdaschool.hackathon_portal.di

import com.lambdaschool.hackathon_portal.ui.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [NavDrawerModule::class])
interface ActivityComponent {

    fun getFragmentComponentBuilder(): FragmentComponent.Builder

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun bindActivity(activity: MainActivity): Builder

        fun build(): ActivityComponent
    }

    fun injectMainActivity(activity: MainActivity)
}