package com.lambdaschool.hackathon_portal.ui

import android.os.Bundle
import android.util.Log
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

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var webAuthProviderLogout: WebAuthProvider.LogoutBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App)
            .appComponent
            .injectMainActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.nav_view.setupWithNavController(
            nav_host_fragment.findNavController()
        )

        nav_view.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when (menuItem.title) {
                "Logout" -> {
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
            }
            true
        }
    }
}