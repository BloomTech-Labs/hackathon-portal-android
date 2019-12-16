package com.lambdaschool.hackathon_portal.fragments.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.lambdaschool.hackathon_portal.MainActivity
import com.lambdaschool.hackathon_portal.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    lateinit var auth0: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
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
    }

    private fun login() {

        activity?.apply {
            auth0 = Auth0(this)
            WebAuthProvider.login(auth0).withScheme("demo").withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain))).start(this, object : AuthCallback {
                    override fun onFailure(dialog: Dialog) {

                    }

                    override fun onFailure(exception: AuthenticationException) {

                    }

                    override fun onSuccess(credentials: Credentials) {
                            Log.i("BIGBRAIN", "Login Successful")
                            Log.i("BIGBRAIN", credentials.accessToken)
                            this@LoginFragment.findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                    }
                })
        }
    }

    private fun logout() {
        activity?.apply {
            WebAuthProvider.logout(auth0!!).withScheme("demo").start(this, object : VoidCallback {
                override fun onSuccess(payload: Void) {}
                override fun onFailure(error: Auth0Exception) { //Log out canceled, keep the user logged in
                   // showNextActivity()
                }
            })
        }
    }

    companion object {
        const val EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS"
        const val EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN"
    }
}
