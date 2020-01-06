package com.lambdaschool.hackathon_portal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.auth0.android.provider.WebAuthProvider
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
    }
}