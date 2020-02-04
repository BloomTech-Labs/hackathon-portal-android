package com.lambdaschool.hackathon_portal.di.activity.modules

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.di.activity.ActivityScope
import com.lambdaschool.hackathon_portal.ui.MainActivity
import dagger.Module
import dagger.Provides

@Module(includes = [ToolbarModule::class])
object NavDrawerModule {

    @ActivityScope
    @Provides
    @JvmStatic
    fun provideDrawerLayoutView(activity: MainActivity): DrawerLayout =
        activity.findViewById(R.id.drawer_layout)

    @ActivityScope
    @Provides
    @JvmStatic
    fun provideActionBarDrawerToggle(activity: MainActivity,
                                     drawerLayout: DrawerLayout,
                                     toolbar: Toolbar): ActionBarDrawerToggle =
        ActionBarDrawerToggle(
            activity,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer)

    @ActivityScope
    @Provides
    @JvmStatic
    fun provideNavController(activity: MainActivity): NavController =
        Navigation.findNavController(activity, R.id.nav_host_fragment)

    @ActivityScope
    @Provides
    @JvmStatic
    fun provideNavigationView(activity: MainActivity): NavigationView =
        activity.findViewById(R.id.nav_view)
}