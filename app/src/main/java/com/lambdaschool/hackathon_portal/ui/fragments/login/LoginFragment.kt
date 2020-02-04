package com.lambdaschool.hackathon_portal.ui.fragments.login

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.ui.fragments.NavDrawerFragment
import com.lambdaschool.hackathon_portal.util.text
import com.lambdaschool.hackathon_portal.util.toastLong
import com.lambdaschool.hackathon_portal.util.visGone
import com.lambdaschool.hackathon_portal.util.visVisible
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
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
        loginViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lockDrawer(true)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_fragment_login_login.setOnClickListener {
            showProgressBarViews(true, getString(R.string.logging_in))
            login()
        }

        if (credentialsManager.hasValidCredentials()) {
            Timber.d("Sending to Dashboard")
            showNextFragment()
        } else {
            showProgressBarViews(false)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        unlockDrawer(true)
    }

    private fun login() {

        activity?.apply {

            webAuthProviderLogin.start(this, object : AuthCallback {
                override fun onFailure(dialog: Dialog) {
                    Timber.d("Login Failed")
                    Timber.d("${dialog.show()}")
                    showProgressBarViews(false)
                    activity?.toastLong("Login Failed - ${dialog.show()}")
                }

                override fun onFailure(exception: AuthenticationException) {
                    Timber.d("Login Failed")
                    Timber.d("Code: ${exception.code} Message: ${exception.message}")
                    showProgressBarViews(false)
                    activity?.toastLong("Login Failed - Code: ${exception.code} Message: ${exception.message}")
                }

                override fun onSuccess(credentials: Credentials) {
                    Timber.d("Login Successful")
                    Timber.d(credentials.accessToken ?: "AccessToken is null")
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
                } else {
                    Timber.d("Failed to set UserAuth0 credentials")
                    showProgressBarViews(false)
                }
            }

            override fun onFailure(error: CredentialsManagerException?) {
                error?.message?.let {
                    activity?.toastLong(it)
                    Timber.d(it)
                    showProgressBarViews(false)
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
                    navigateAndPopUpTo(
                        Bundle(), R.id.nav_login, true, R.id.nav_dashboard
                    )
                } else {
                    showProgressBarViews(false)
                }
            })
        }
    }

    private fun setNavDrawerHeader(response: User) {
        setNavDrawerHeaderTitle(response.username)
        setNavDrawerHeaderSubTitle(response.email)
        setNavDrawerHeaderImage(loginViewModel.getUserAuth0PictureUrl())
    }

    private fun showProgressBarViews(boolean: Boolean, setText: String? = null) {
        if (boolean) {
            progress_bar_login.visVisible()
            text_view_login_progress.visVisible()
            if (setText != null) {
                text_view_login_progress.text(setText)
            }
            button_fragment_login_login.visGone()
        } else {
            progress_bar_login.visGone()
            text_view_login_progress.visGone()
            button_fragment_login_login.visVisible()
        }
    }
}