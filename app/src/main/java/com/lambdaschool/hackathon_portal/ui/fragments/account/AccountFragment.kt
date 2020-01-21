package com.lambdaschool.hackathon_portal.ui.fragments.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import com.google.gson.JsonObject

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.LoggedInUser
import com.lambdaschool.hackathon_portal.ui.fragments.NavDrawerFragment
import com.lambdaschool.hackathon_portal.util.SelectiveJsonObject
import com.lambdaschool.hackathon_portal.util.toastLong
import com.lambdaschool.hackathon_portal.util.toastShort
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : NavDrawerFragment() {

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
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
                            activity?.toastLong("Successfully updated account info")
                            navHeaderTitleTextView.text = edit_text_username.text.toString()
                            navHeaderSubtitleTextView.text = edit_text_email_address.text.toString()
                            navigateToDashboardFragment()
                        }
                        else {
                            activity?.toastShort("Failed to update account info")
                        }
                    }
                })
            } else {
                activity?.toastShort("Nothing to update")
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
                                navController.navigate(R.id.nav_logout)
                            } else {
                                activity?.toastShort("Failed to delete your account")
                            }
                        }
                    })
                }

                .setNegativeButton("No") { _, _ -> }
                .create()
                .show()
        }
    }

    private fun navigateToDashboardFragment() {
        val bundle = Bundle()
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_dashboard, true)
            .build()
        navController.navigate(
            R.id.nav_dashboard,
            bundle,
            navOptions)
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
