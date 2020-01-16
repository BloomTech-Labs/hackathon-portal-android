package com.lambdaschool.hackathon_portal.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationView
import com.lambdaschool.hackathon_portal.R
import javax.inject.Inject

abstract class NavDrawerFragment: BaseFragment() {

    @Inject
    lateinit var drawerLayout: DrawerLayout
    @Inject
    lateinit var toggle: ActionBarDrawerToggle
    @Inject
    lateinit var navController: NavController
    @Inject
    lateinit var navView: NavigationView

    val navHeaderTitleTextView: TextView by lazy {
        navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_title)
    }
    val navHeaderSubtitleTextView: TextView by lazy {
        navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_subtitle)
    }
    val navHeaderImageView: ImageView by lazy {
        navView.getHeaderView(0).findViewById<ImageView>(R.id.nav_header_image)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentComponent.injectNavDrawerFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }
}