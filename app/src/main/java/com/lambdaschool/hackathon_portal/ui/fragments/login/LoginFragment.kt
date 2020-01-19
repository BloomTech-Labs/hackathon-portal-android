package com.lambdaschool.hackathon_portal.ui.fragments.login

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
import com.lambdaschool.hackathon_portal.model.UserD
import com.lambdaschool.hackathon_portal.ui.fragments.NavDrawerFragment
import com.lambdaschool.hackathon_portal.util.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment : NavDrawerFragment() {

    @Inject
    lateinit var webAuthProviderLogin: WebAuthProvider.Builder
    @Inject
    lateinit var credentialsManager: SecureCredentialsManager

    private lateinit var loginViewModel: LoginViewModel
    private val TAG = "LOGIN FRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectLoginFragment(this)
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        drawerLayout._lockDrawer(toggle)
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
        drawerLayout._unlockDrawer(toggle)
    }

    private fun login() {

        activity?.apply {

            webAuthProviderLogin.start(this, object : AuthCallback {
                override fun onFailure(dialog: Dialog) {
                    Log.i(TAG, "Login Failed")
                    Log.i(TAG, "${dialog.show()}")
                    activity?._toastLong("Login Failed - ${dialog.show()}")
                }

                override fun onFailure(exception: AuthenticationException) {
                    Log.i(TAG, "Login Failed")
                    Log.i(TAG, "Code: ${exception.code} Message: ${exception.message}")
                    activity?._toastLong("Login Failed - Code: ${exception.code} Message: ${exception.message}")
                }

                override fun onSuccess(credentials: Credentials) {
                    Log.i(TAG, "Login Successful")
                    Log.i(TAG, credentials.accessToken ?: "AccessToken is null")
                    credentialsManager.saveCredentials(credentials)
                    GlobalScope.launch(Main) {
                        showNextFragment()
                    }
                }
            })
        }
    }

    private fun showNextFragment() {
        credentialsManager.getCredentials(object : BaseCallback<Credentials, CredentialsManagerException?> {

            override fun onSuccess(credentials: Credentials) {
                if (setUserAuth0(credentials)) {
                    getUserData()
                }
            }

            override fun onFailure(error: CredentialsManagerException?) {
                error?.message?.let {
                    activity?._toastLong(it)
                    Log.d(TAG, it)
                }
            }
        })
    }

    private fun setUserAuth0(credentials: Credentials): Boolean {
        val jwt = JWT(credentials.idToken!!)
        val claims: Map<String, Claim> = jwt.claims

        // Using a counter to ensure that the AccessToken and Id get set,
        // otherwise nothing past this part will work properly.
        var counter = 0

        claims["picture"]?.asString()?.let {
            loginViewModel.setUserAuth0PictureUrl(it)
        }

        credentials.accessToken?.let {
            loginViewModel.setUserAuth0AccessToken(it)
            counter++
        }

        claims["sub"]?.asString()?.let {
            loginViewModel.setUserAuth0Id(it.split("|")[1].toInt())
            counter++
        }
        return counter > 1
    }

    private fun getUserData() {
        activity?.apply {
            loginViewModel.getUser().observe(this, Observer { response ->
                if (response != null) {
                    setNavDrawerHeader(response)
                    navController._navigateAndPopUpTo(
                        Bundle(), R.id.nav_login, true, R.id.nav_dashboard)
                }
            })
        }
    }

    private fun setNavDrawerHeader(response: UserD.UserE) {
        setNavDrawerHeaderTitle(response.username)
        setNavDrawerHeaderSubTitle(response.email)
        setNavDrawerHeaderImage(loginViewModel.getUserAuth0PictureUrl())
    }
}