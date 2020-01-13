package com.lambdaschool.hackathon_portal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambdaschool.hackathon_portal.ui.fragments.account.AccountViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.create.CreateHackathonViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.dashboard.DashboardViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.detail.DetailViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.edit.EditHackathonFragmentViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.userhackathons.UserHackathonsViewModel
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelsModule {

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(CreateHackathonViewModel::class)
    abstract fun bindsCreateHackathonViewModel(viewModel: CreateHackathonViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindsDashboardViewModel(viewModel: DashboardViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindsAccountViewModel(viewModel: AccountViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(EditHackathonFragmentViewModel::class)
    abstract fun bindsEditHackathonViewModel(viewModel: EditHackathonFragmentViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(UserHackathonsViewModel::class)
    abstract fun bindsUserHackathonsViewModel(viewModel: UserHackathonsViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindsDetailViewModel(viewModel: DetailViewModel): ViewModel
}