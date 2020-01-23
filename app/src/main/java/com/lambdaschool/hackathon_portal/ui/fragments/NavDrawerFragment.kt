package com.lambdaschool.hackathon_portal.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.lambdaschool.hackathon_portal.R
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * This NavDrawerFragment extends the BaseFragment functionality for those fragments needing to
 * access things for the Navigation Drawer.
 * */
abstract class NavDrawerFragment: BaseFragment() {

    @Inject
    lateinit var drawerLayout: DrawerLayout
    @Inject
    lateinit var toggle: ActionBarDrawerToggle
    @Inject
    lateinit var navView: NavigationView

    private val navHeaderTitleTextView by lazy {
        navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_title)
    }
    private val navHeaderSubtitleTextView by lazy {
        navView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_subtitle)
    }
    private val navHeaderImageView by lazy {
        navView.getHeaderView(0).findViewById<ImageView>(R.id.nav_header_image)
    }

    fun setNavDrawerHeaderTitle(string: String?) {
        if (!string.isNullOrEmpty()) {
            navHeaderTitleTextView.text = string
        }
    }

    fun setNavDrawerHeaderSubTitle(string: String) {
        navHeaderSubtitleTextView.text = string
    }

    fun setNavDrawerHeaderImage(pictureUrl: String) {
        if (pictureUrl.isNotEmpty()) {
            Picasso.get().load(pictureUrl).into(navHeaderImageView)
        }
    }

    fun resetNavDrawerHeader() {
        navHeaderTitleTextView.text = getString(R.string.nav_header_title)
        navHeaderSubtitleTextView.text = getString(R.string.nav_header_subtitle)
        navHeaderImageView.setImageResource(R.drawable.ic_launcher_background)
    }

    /**
     * Locks the Navigation Drawer and, if true is passed, it will disable the
     * toggle.
     *
     * Passing false will lock the Navigation Drawer only.
     *
     * @param disableToggle: Boolean
     * */
    fun lockDrawer(disableToggle: Boolean) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        if (disableToggle) {
            toggle.isDrawerIndicatorEnabled = false
        }
    }

    /**
     * Unlocks the Navigation Drawer and, if true is passed it will enable the
     * toggle.
     *
     * Passing false will unlock the Navigation Drawer only.
     *
     * @param enableToggle: Boolean
     * */
    fun unlockDrawer(enableToggle: Boolean) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        if (enableToggle) {
            toggle.isDrawerIndicatorEnabled = true
        }
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