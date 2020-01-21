package com.lambdaschool.hackathon_portal.ui.fragments.logout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavOptions
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.wipeCurrentUser
import com.lambdaschool.hackathon_portal.model.wipeLoggedInUser
import com.lambdaschool.hackathon_portal.ui.fragments.NavDrawerFragment
import javax.inject.Inject

class LogoutFragment : NavDrawerFragment() {

    @Inject
    lateinit var webAuthProviderLogout: WebAuthProvider.LogoutBuilder
    @Inject
    lateinit var credentialsManager: SecureCredentialsManager

    private val TAG = "LOGOUT FRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectLogoutFragment(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        activity?.apply {
            webAuthProviderLogout.start(this, object : VoidCallback {
                override fun onSuccess(payload: Void?) {
                    Log.i(TAG, "Success")
                    credentialsManager.clearCredentials()
                    wipeCurrentUser()
                    wipeLoggedInUser()
                    navigateToLoginFragment()
                }

                override fun onFailure(error: Auth0Exception?) {
                    Log.i(TAG, "Failure ${error?.message}")
                    unlockDrawer(false)
                    navigateAndPopUpTo(
                        Bundle(), R.id.nav_dashboard, true, R.id.nav_dashboard
                    )
                    activity?.toastLong("Logout Failed:\n${error?.message}")
                }
            })
        }
    }

    private fun navigateToLoginFragment() {
        val bundle = Bundle()
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_graph, true)
            .build()

        navController.navigate(
            R.id.nav_login,
            bundle,
            navOptions)
    }
}
