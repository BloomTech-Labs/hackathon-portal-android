package com.lambdaschool.hackathon_portal.di

import androidx.fragment.app.Fragment
import com.lambdaschool.hackathon_portal.fragments.login.LoginFragment
import com.lambdaschool.hackathon_portal.fragments.login.LoginModule
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LoginModule::class])
interface FragmentComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun bindFragment(fragment: Fragment): Builder

        fun build(): FragmentComponent
    }

    fun injectLoginFragment(fragment: LoginFragment)
}