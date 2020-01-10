package com.lambdaschool.hackathon_portal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambdaschool.hackathon_portal.ui.fragments.create.CreateHackathonViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.dashboard.DashboardViewModel
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
}