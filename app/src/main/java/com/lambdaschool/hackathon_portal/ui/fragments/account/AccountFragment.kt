package com.lambdaschool.hackathon_portal.ui.fragments.account


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.google.gson.JsonObject

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.LoggedInUser
import com.lambdaschool.hackathon_portal.model.wipeCurrentUser
import com.lambdaschool.hackathon_portal.ui.MainActivity
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_account.*
import javax.inject.Inject

class AccountFragment : Fragment() {

    private val fragmentComponent by lazy {
        (activity as MainActivity)
            .activityComponent
            .getFragmentComponentBuilder()
            .bindFragment(this)
            .build()
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    @Inject
    lateinit var webAuthProviderLogout: WebAuthProvider.LogoutBuilder
    @Inject
    lateinit var credentialsManager: SecureCredentialsManager
    @Inject
    lateinit var navController: NavController
    @Inject
    lateinit var headerView: View

    private val navHeaderTitleTextView by lazy {
        headerView.findViewById<TextView>(R.id.nav_header_title)
    }
    private val navHeaderSubtitleTextView by lazy {
        headerView.findViewById<TextView>(R.id.nav_header_subtitle)
    }

    lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent.injectAccountFragment(this)
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(AccountViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserInfoToEditTextFields()

        fab_save_user.setOnClickListener {
            val jsonObject = buildJsonObject()
            if (jsonObject != null) {
                accountViewModel.updateUser(jsonObject).observe(this, Observer {
                    if (it != null) {
                        if (it) {
                            activity?.apply {
                                Toast.makeText(this, "Successfully updated account info", Toast.LENGTH_LONG).show()
                            }
                            navHeaderTitleTextView.text = edit_text_username.text.toString()
                            navHeaderSubtitleTextView.text = edit_text_email_address.text.toString()
                            navController.popBackStack()
                        }
                        else {
                            activity?.apply {
                                Toast.makeText(this, "Failed to update account info", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
            }
        }

        fab_delete_user.setOnClickListener {
            val title = "Delete User?"
            val msg = "Are you sure you would like to delete this account?"

            AlertDialog.Builder(context!!)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Yes") { _, _ ->

                    accountViewModel.deleteUser().observe(this, Observer {
                        if (it != null) {
                            if (it) {
                                activity?.apply {
                                    webAuthProviderLogout.start(this, object : VoidCallback {
                                        override fun onSuccess(payload: Void?) {
                                            Log.i("Account Fragment", "Successful logout")
                                            credentialsManager.clearCredentials()
                                            wipeCurrentUser()
                                            navController.navigate(R.id.loginFragment)
                                        }

                                        override fun onFailure(error: Auth0Exception?) {
                                            Log.i("Account Fragment", "Failure ${error?.message}")
                                        }
                                    })
                                }
                            } else {
                                activity?.apply {
                                    Toast.makeText(this, "Failed to delete your account", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    })
                }

                .setNegativeButton("No") { _, _ -> }
                .create()
                .show()
        }
    }

    private fun loadUserInfoToEditTextFields() {
        edit_text_user_first_name.setText(LoggedInUser.user.first_name)
        edit_text_user_last_name.setText(LoggedInUser.user.last_name)
        edit_text_username.setText(LoggedInUser.user.username)
        edit_text_email_address.setText(LoggedInUser.user.email)
    }

    private fun buildJsonObject(): JsonObject? {
        val firstName = edit_text_user_first_name.text.toString()
        val lastName = edit_text_user_last_name.text.toString()
        val username = edit_text_username.text.toString()
        val email = edit_text_email_address.text.toString()

        var counter = 0
        val jsonObject = JsonObject()
        if (firstName != LoggedInUser.user.first_name) {
            jsonObject.addProperty("first_name", firstName)
            counter++
        }

        if (lastName != LoggedInUser.user.last_name) {
            jsonObject.addProperty("last_name", lastName)
            counter++
        }

        if (username.isNotEmpty() && username != LoggedInUser.user.username) {
            jsonObject.addProperty("username", username)
            counter++
        }

        if (email.isNotEmpty() && email != LoggedInUser.user.email) {
            jsonObject.addProperty("email", email)
            counter++
        }

        return if (counter > 0) {
            jsonObject
        }
        else {
            activity?.apply {
                Toast.makeText(this, "Nothing to update", Toast.LENGTH_SHORT).show()
            }
            null
        }
    }
}
