package com.lambdaschool.hackathon_portal.di.activity.modules

import androidx.appcompat.widget.Toolbar
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.di.activity.ActivityScope
import com.lambdaschool.hackathon_portal.ui.MainActivity
import dagger.Module
import dagger.Provides

@Module
object ToolbarModule {

    @ActivityScope
    @Provides
    @JvmStatic
    fun provideToolbarView(activity: MainActivity): Toolbar {
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(toolbar)
        return toolbar
    }
}