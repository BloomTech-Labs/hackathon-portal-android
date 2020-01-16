package com.lambdaschool.hackathon_portal.di

import androidx.fragment.app.Fragment
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.ui.fragments.NavDrawerFragment
import com.lambdaschool.hackathon_portal.ui.fragments.login.LoginFragment
import com.lambdaschool.hackathon_portal.ui.fragments.login.LoginModule
import com.lambdaschool.hackathon_portal.ui.fragments.logout.LogoutFragment
import com.lambdaschool.hackathon_portal.ui.fragments.logout.LogoutModule
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LoginModule::class, LogoutModule::class])
interface FragmentComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun bindFragment(fragment: Fragment): Builder

        fun build(): FragmentComponent
    }

    fun injectLoginFragment(fragment: LoginFragment)
    fun injectLogoutFragment(fragment: LogoutFragment)
    fun injectBaseFragment(fragment: BaseFragment)
    fun injectNavDrawerFragment(fragment: NavDrawerFragment)

}