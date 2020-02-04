package com.lambdaschool.hackathon_portal.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.lambdaschool.hackathon_portal.App
import com.lambdaschool.hackathon_portal.R
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val activityComponent by lazy {
        (application as App)
            .appComponent
            .getActivityComponentBuilder()
            .bindActivity(this)
            .build()
    }

    @Inject
    lateinit var drawerLayout: DrawerLayout
    @Inject
    lateinit var toggle: ActionBarDrawerToggle
    @Inject
    lateinit var navController: NavController
    @Inject
    lateinit var navView: NavigationView

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent.injectMainActivity(this)

        // Must call so DI modules build & set the AppBar
        toggle

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dashboard,
                R.id.nav_create_hackathon,
                R.id.nav_user_hackathons,
                R.id.nav_hackathon_details,
                R.id.nav_add_admin,
                R.id.nav_project_details,
                R.id.nav_create_project,
                R.id.nav_edit_hackathon,
                R.id.nav_account/*,
                R.id.settingsFragment*/,
                R.id.nav_logout
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(navView)) {
            drawerLayout.closeDrawers()

            //TODO: Need to figure out logic to determine if the current fragment is the
            // DashboardFragment such that when the back button is pressed we can call finish()
            // to close out the application instead of the typical super.onBackPressed() call which
            // does not play well with the Auth0 rigmarole.
/*        } else if (navController.currentDestination = DashboardFragment) {
            finish()
        */} else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}