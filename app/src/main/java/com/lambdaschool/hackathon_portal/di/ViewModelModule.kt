package com.lambdaschool.hackathon_portal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambdaschool.hackathon_portal.ui.fragments.add.AddHackathonViewModel
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
    @ViewModelKey(AddHackathonViewModel::class)
    abstract fun bindsAddHackathonViewModel(viewModel: AddHackathonViewModel): ViewModel
}