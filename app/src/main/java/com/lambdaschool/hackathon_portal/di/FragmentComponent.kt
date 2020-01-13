package com.lambdaschool.hackathon_portal.di

import androidx.fragment.app.Fragment
import com.lambdaschool.hackathon_portal.ui.fragments.account.AccountFragment
import com.lambdaschool.hackathon_portal.ui.fragments.create.CreateHackathonFragment
import com.lambdaschool.hackathon_portal.ui.fragments.dashboard.DashboardFragment
import com.lambdaschool.hackathon_portal.ui.fragments.details.EditHackathonFragment
import com.lambdaschool.hackathon_portal.ui.fragments.login.LoginFragment
import com.lambdaschool.hackathon_portal.ui.fragments.login.LoginModule
import com.lambdaschool.hackathon_portal.ui.fragments.settings.SettingsFragment
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
    fun injectDashboardFragment(fragment: DashboardFragment)
    fun injectAccountFragment(fragment: AccountFragment)
    fun injectSettingsFragment(fragment: SettingsFragment)
    fun injectCreateHackathonFragment(fragment: CreateHackathonFragment)
    fun injectEditHackathonFragment(fragment: EditHackathonFragment)
}