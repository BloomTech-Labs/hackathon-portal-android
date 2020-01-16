package com.lambdaschool.hackathon_portal.di

import androidx.fragment.app.Fragment
import com.lambdaschool.hackathon_portal.ui.fragments.account.AccountFragment
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.ui.fragments.NavDrawerFragment
import com.lambdaschool.hackathon_portal.ui.fragments.create.CreateHackathonFragment
import com.lambdaschool.hackathon_portal.ui.fragments.dashboard.DashboardFragment
import com.lambdaschool.hackathon_portal.ui.fragments.detail.AdminFragment
import com.lambdaschool.hackathon_portal.ui.fragments.detail.DescriptionFragment
import com.lambdaschool.hackathon_portal.ui.fragments.detail.DetailFragment
import com.lambdaschool.hackathon_portal.ui.fragments.detail.TeamFragment
import com.lambdaschool.hackathon_portal.ui.fragments.edit.EditHackathonFragment
import com.lambdaschool.hackathon_portal.ui.fragments.login.LoginFragment
import com.lambdaschool.hackathon_portal.ui.fragments.login.LoginModule
import com.lambdaschool.hackathon_portal.ui.fragments.logout.LogoutFragment
import com.lambdaschool.hackathon_portal.ui.fragments.logout.LogoutModule
import com.lambdaschool.hackathon_portal.ui.fragments.settings.SettingsFragment
import com.lambdaschool.hackathon_portal.ui.fragments.userhackathons.UserHackathonsFragment
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
    fun injectDashboardFragment(fragment: DashboardFragment)
    fun injectAccountFragment(fragment: AccountFragment)
    fun injectSettingsFragment(fragment: SettingsFragment)
    fun injectCreateHackathonFragment(fragment: CreateHackathonFragment)
    fun injectEditHackathonFragment(fragment: EditHackathonFragment)
    fun injectUserHackathonsFragment(fragment: UserHackathonsFragment)
    fun injectDetailFragment(fragment: DetailFragment)
    fun injectDescriptionFragment(fragment: DescriptionFragment)
    fun injectAdminFragment(fragment: AdminFragment)
    fun injectTeamFragment(fragment: TeamFragment)
    fun injectLogoutFragment(fragment: LogoutFragment)
    fun injectBaseFragment(fragment: BaseFragment)
    fun injectNavDrawerFragment(fragment: NavDrawerFragment)

}