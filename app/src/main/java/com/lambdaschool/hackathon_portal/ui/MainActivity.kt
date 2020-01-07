package com.lambdaschool.hackathon_portal.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.auth0.android.Auth0Exception
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.lambdaschool.hackathon_portal.App
import com.lambdaschool.hackathon_portal.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavDrawerInterface {

    @Inject
    lateinit var webAuthProviderLogout: WebAuthProvider.LogoutBuilder
    lateinit var drawerLayout: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App)
            .appComponent
            .injectMainActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        drawerLayout = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        nav_view.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.nav_drawer_logout -> {
                    webAuthProviderLogout.start(this, object : VoidCallback {
                        override fun onSuccess(payload: Void?) {
                            Log.i("Nav Drawer", "Success")
                            App.credentialsManager.clearCredentials()
                            nav_host_fragment.findNavController().navigate(R.id.loginFragment)
                        }

                        override fun onFailure(error: Auth0Exception?) {
                            Log.i("Nav Drawer", "Failure ${error?.message}")
                        }
                    })
                }
                R.id.nav_drawer_account -> {
                    nav_host_fragment.findNavController().navigate(R.id.accountFragment)
                    title = "Account Details"
                }
                R.id.nav_drawer_settings -> {
                    nav_host_fragment.findNavController().navigate(R.id.settingsFragment)
                    title = "Settings"
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun lockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toggle.isDrawerIndicatorEnabled = false
    }

    override fun unlockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        toggle.isDrawerIndicatorEnabled = true
    }
}