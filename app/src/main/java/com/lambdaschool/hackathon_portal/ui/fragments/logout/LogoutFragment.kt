package com.lambdaschool.hackathon_portal.ui.fragments.logout

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.wipeCurrentUser
import com.lambdaschool.hackathon_portal.ui.MainActivity
import javax.inject.Inject

class LogoutFragment : Fragment() {

    private val fragmentComponent by lazy {
        (activity as MainActivity)
            .activityComponent
            .getFragmentComponentBuilder()
            .bindFragment(this)
            .build()
    }

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

        activity?.apply {
            webAuthProviderLogout.start(this, object : VoidCallback {
                override fun onSuccess(payload: Void?) {
                    Log.i(TAG, "Success")
                    credentialsManager.clearCredentials()
                    wipeCurrentUser()
                }

                override fun onFailure(error: Auth0Exception?) {
                    Log.i(TAG, "Failure ${error?.message}")

                    activity?.apply {
                        Toast.makeText(this, "Logout Failed", Toast.LENGTH_SHORT).show()
                    }

                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}
