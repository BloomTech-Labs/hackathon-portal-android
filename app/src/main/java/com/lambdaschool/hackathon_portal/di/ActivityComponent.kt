package com.lambdaschool.hackathon_portal.di

import androidx.fragment.app.Fragment
import com.lambdaschool.hackathon_portal.ui.fragments.login.LoginFragment
import com.lambdaschool.hackathon_portal.ui.fragments.login.LoginModule
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LoginModule::class])
interface ActivityComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun bindFragment(fragment: Fragment): Builder

        fun build(): ActivityComponent
    }

    fun injectLoginFragment(fragment: LoginFragment)
}