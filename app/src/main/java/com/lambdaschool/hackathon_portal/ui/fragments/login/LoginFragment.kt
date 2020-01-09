package com.lambdaschool.hackathon_portal.ui.fragments.login

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.callback.BaseCallback
import com.auth0.android.jwt.Claim
import com.auth0.android.jwt.JWT
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.CurrentUser
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginFragment : Fragment() {

    private val fragmentComponent by lazy {
        (activity as MainActivity)
            .activityComponent
            .getFragmentComponentBuilder()
            .bindFragment(this)
            .build()
    }

    @Inject
    lateinit var webAuthProviderLogin: WebAuthProvider.Builder
    @Inject
    lateinit var credentialsManager: SecureCredentialsManager
    @Inject
    lateinit var drawerLayout: DrawerLayout
    @Inject
    lateinit var toggle: ActionBarDrawerToggle
    @Inject
    lateinit var headerView: View

    private val navHeaderTitleTextView by lazy {
        headerView.findViewById<TextView>(R.id.nav_header_title)
    }
    private val navHeaderSubtitleTextView by lazy {
        headerView.findViewById<TextView>(R.id.nav_header_subtitle)
    }
    private val navHeaderImageView by lazy {
        headerView.findViewById<ImageView>(R.id.nav_header_image)
    }

    private val TAG = "LOGIN FRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectLoginFragment(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toggle.isDrawerIndicatorEnabled = false
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_login.setOnClickListener {
            login()
        }

        if (credentialsManager.hasValidCredentials()) {
            Log.i(TAG, "Sending to Dashboard")
            showNextFragment()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        toggle.isDrawerIndicatorEnabled = true
    }

    private fun login() {

        activity?.apply {

            webAuthProviderLogin.start(this, object : AuthCallback {
                override fun onFailure(dialog: Dialog) {
                    Log.i(TAG, "Login Failed")
                    Log.i(TAG, "${dialog.show()}")

                    activity?.apply {
                        Toast.makeText(this, "Login Failed - ${dialog.show()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(exception: AuthenticationException) {
                    Log.i(TAG, "Login Failed")
                    Log.i(TAG, "Code: ${exception.code} Message: ${exception.message}")

                    activity?.apply {
                        Toast.makeText(this, "Login Failed - Code: ${exception.code} Message: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onSuccess(credentials: Credentials) {
                    Log.i(TAG, "Login Successful")
                    Log.i(TAG, credentials.accessToken ?: "AccessToken is null")
                    credentialsManager.saveCredentials(credentials)
                    showNextFragment()
                }
            })
        }
    }

    private fun showNextFragment() {
        credentialsManager.getCredentials(object : BaseCallback<Credentials, CredentialsManagerException?> {

            //Using coroutines to run saving current user and navigating with nav controller on main
            //thread because the onSuccess call back runs on a background thread

            override fun onSuccess(credentials: Credentials) {
                GlobalScope.launch(Main) { setCurrentUser(credentials) }
                setCurrentUser(credentials)
                val bundle = Bundle()
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment, true)
                    .build()
                GlobalScope.launch(Main) { findNavController()
                    .navigate(R.id.action_loginFragment_to_dashboardFragment, bundle, navOptions)
                }
            }

            override fun onFailure(error: CredentialsManagerException?) {
                activity?.apply {
                    Toast.makeText(this, error?.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setCurrentUser(credentials: Credentials) {
        val jwt = JWT(credentials.idToken!!)
        val claims: Map<String, Claim> = jwt.claims

        claims.get("sub")?.asString()?.let {
            CurrentUser.currentUser.id = it.split("|")[1]
        }

        claims.get("name")?.asString()?.let {
            CurrentUser.currentUser.name = it
            GlobalScope.launch(Main) {
                navHeaderTitleTextView.text = it
            }
        }

        claims.get("email")?.asString()?.let {
            CurrentUser.currentUser.email = it
            GlobalScope.launch(Main) {
                navHeaderSubtitleTextView.text = it
            }
        }

        claims.get("picture")?.asString()?.let {
            CurrentUser.currentUser.pictureURL = it
            GlobalScope.launch(Main) {
                Picasso.get().load(it).into(navHeaderImageView)
            }
        }
    }
}