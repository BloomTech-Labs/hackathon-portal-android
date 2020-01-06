package com.lambdaschool.hackathon_portal.fragments.dashboard


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.lambdaschool.hackathon_portal.App

import com.lambdaschool.hackathon_portal.R
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    @Inject
    lateinit var webAuthProviderLogout: WebAuthProvider.LogoutBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App)
            .appComponent
            .injectDashboardFragment(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        button_logout.setOnClickListener {
            activity.apply {
                webAuthProviderLogout.start(this, object: VoidCallback {

                    override fun onSuccess(payload: Void?) {
                        Log.i("Dashboard Fragment", "Success")
                        App.credentialsManager.clearCredentials()
                        this@DashboardFragment.findNavController().navigateUp()
                    }

                    override fun onFailure(error: Auth0Exception?) {
                        Log.i("Dashboard Fragment", "Failure ${error?.message}")
                    }
                })
            }
        }
    }
}
