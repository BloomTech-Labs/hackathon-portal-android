package com.lambdaschool.hackathon_portal.ui.fragments.logout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.ui.fragments.NavDrawerFragment
import com.lambdaschool.hackathon_portal.util.toastLong
import timber.log.Timber
import javax.inject.Inject

class LogoutFragment : NavDrawerFragment() {

    @Inject
    lateinit var webAuthProviderLogout: WebAuthProvider.LogoutBuilder
    @Inject
    lateinit var credentialsManager: SecureCredentialsManager

    private lateinit var logoutViewModel: LogoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectLogoutFragment(this)
        super.onCreate(savedInstanceState)
        logoutViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(LogoutViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lockDrawer(false)

        activity?.apply {
            webAuthProviderLogout.start(this, object : VoidCallback {
                override fun onSuccess(payload: Void?) {
                    Timber.d("Logout Success")
                    credentialsManager.clearCredentials()
                    logoutViewModel.performLogout()
                    resetNavDrawerHeader()
                    navigateAndPopUpTo(
                        Bundle(), R.id.nav_graph, true, R.id.nav_login
                    )
                }

                override fun onFailure(error: Auth0Exception?) {
                    Timber.d("Failure ${error?.message}")
                    unlockDrawer(false)
                    navigateAndPopUpTo(
                        Bundle(), R.id.nav_dashboard, true, R.id.nav_dashboard
                    )
                    activity?.toastLong("Logout Failed:\n${error?.message}")
                }
            })
        }
    }
}
