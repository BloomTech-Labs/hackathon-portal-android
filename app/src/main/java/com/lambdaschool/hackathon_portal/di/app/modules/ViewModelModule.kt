package com.lambdaschool.hackathon_portal.di.app.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lambdaschool.hackathon_portal.di.app.ViewModelKey
import com.lambdaschool.hackathon_portal.ui.fragments.account.AccountViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.addadmin.AddAdminViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.create.CreateHackathonViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.create.CreateProjectViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.dashboard.DashboardViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.detail.DetailViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.edit.EditHackathonViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.login.LoginViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.logout.LogoutViewModel
import com.lambdaschool.hackathon_portal.ui.fragments.projectdetail.ProjectDetailViewModel
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
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindsLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(LogoutViewModel::class)
    abstract fun bindsLogoutViewModel(viewModel: LogoutViewModel): ViewModel

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
    @ViewModelKey(EditHackathonViewModel::class)
    abstract fun bindsEditHackathonViewModel(viewModel: EditHackathonViewModel): ViewModel

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

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(ProjectDetailViewModel::class)
    abstract fun bindsProjectDetailViewModel(viewModel: ProjectDetailViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(AddAdminViewModel::class)
    abstract fun bindsAddAdminViewModel(viewModel: AddAdminViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(CreateProjectViewModel::class)
    abstract fun bindsCreateProjectViewModel(viewModel: CreateProjectViewModel): ViewModel
}