package com.lambdaschool.hackathon_portal.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.lambdaschool.hackathon_portal.App
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.wipeCurrentUser
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val activityComponent by lazy {
        (application as App)
            .appComponent
            .getActivityComponentBuilder()
            .bindActivity(this)
            .build()
    }

//    @Inject
//    lateinit var webAuthProviderLogout: WebAuthProvider.LogoutBuilder
//    @Inject
//    lateinit var credentialsManager: SecureCredentialsManager
    @Inject
    lateinit var drawerLayout: DrawerLayout
    @Inject
    lateinit var toggle: ActionBarDrawerToggle
    @Inject
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent.injectMainActivity(this)

        drawerLayout.addDrawerListener(toggle)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        nav_view.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when (menuItem.itemId) {

                R.id.nav_drawer_dashboard -> {
                    //TODO: Add logic to only execute if the current fragment is NOT the one
                    // being selected
                    //if (navController.currentDestination != DashboardFragment) {
                    navController.navigate(R.id.dashboardFragment)
                    //}
                }

                R.id.nav_drawer_create_hackathon -> {
                    //TODO: Add logic to only execute if the current fragment is not the one
                    // being selected
                    //if (navController.currentDestination != CreateHackathonFragment) {
                    navController.navigate(R.id.addHackathonFragment)
                    //}
                }

                R.id.nav_drawer_account -> {
                    //TODO: Add logic to only execute if the current fragment is not the one
                    // being selected
                    //if (navController.currentDestination != AccountFragment) {
                    navController.navigate(R.id.accountFragment)
                    //}
                }

                R.id.nav_drawer_my_hackathons -> {
                    navController.navigate(R.id.userHackathonsFragment)
                }

                /*R.id.nav_drawer_settings -> {
                    //TODO: Add logic to only execute if the current fragment is not the one
                    // being selected
                    //if (navController.currentDestination != SettingsFragment) {
                    navController.navigate(R.id.settingsFragment)
                    //}
                }*/
                
                R.id.nav_drawer_logout -> {
//                    webAuthProviderLogout.start(this, object : VoidCallback {
//                        override fun onSuccess(payload: Void?) {
//                            Log.i("Nav Drawer", "Success")
//                            credentialsManager.clearCredentials()
//                            wipeCurrentUser()
                            navController.navigate(R.id.logoutFragment)
//                        }

//                        override fun onFailure(error: Auth0Exception?) {
//                            Log.i("Nav Drawer", "Failure ${error?.message}")
//                        }
//                    })
                }

            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(nav_view)) {
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
}