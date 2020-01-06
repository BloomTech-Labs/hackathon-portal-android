package com.lambdaschool.hackathon_portal.fragments.dashboard


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0Exception
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.lambdaschool.hackathon_portal.App

import com.lambdaschool.hackathon_portal.R
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

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
