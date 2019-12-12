package com.lambdaschool.hackathon_portal.di

import androidx.lifecycle.ViewModelProvider
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelsModule {

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}