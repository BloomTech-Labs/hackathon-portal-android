package com.lambdaschool.hackathon_portal.ui.fragments.login

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.callback.BaseCallback
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.lambdaschool.hackathon_portal.App
import com.lambdaschool.hackathon_portal.R
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject
class LoginFragment : Fragment() {

    val TAG = "LOGIN FRAGMENT"

    @Inject
    lateinit var webAuthProviderLogin: WebAuthProvider.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App)
            .appComponent
            .getFragmentComponentBuilder()
            .bindFragment(this)
            .build()
            .injectLoginFragment(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_login.setOnClickListener {
            login()
        }

        if (App.credentialsManager.hasValidCredentials()) {
            Log.i("Login Fragment", "Sending to Dashboard")
            showNextFragment()
        }
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
                    Log.i(TAG, credentials.accessToken)
                    App.credentialsManager.saveCredentials(credentials)
                    showNextFragment()
                }
            })
        }
    }

    private fun showNextFragment() {
        App.credentialsManager.getCredentials(object : BaseCallback<Credentials, CredentialsManagerException?> {

            override fun onSuccess(credentials: Credentials) {
                val bundle = Bundle()
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment, true)
                    .build()
                this@LoginFragment.findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment, bundle, navOptions)
            }

            override fun onFailure(error: CredentialsManagerException?) {
                activity?.apply {
                    Toast.makeText(this, error?.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}