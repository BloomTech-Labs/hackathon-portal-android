package com.lambdaschool.hackathon_portal.ui.fragments.account


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.UserD
import com.lambdaschool.hackathon_portal.ui.fragments.NavDrawerFragment
import com.lambdaschool.hackathon_portal.util.SelectiveJsonObject
import com.lambdaschool.hackathon_portal.util._navigateAndPopUpTo
import com.lambdaschool.hackathon_portal.util._toastLong
import com.lambdaschool.hackathon_portal.util._toastShort
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : NavDrawerFragment() {

    private lateinit var accountViewModel: AccountViewModel
    private var TAG = "ACCOUNT FRAGMENT"

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
        val user = accountViewModel.getUserObjectFromRepository()
        loadUserInfoToEditTextFields(user)

        fab_save_user.setOnClickListener {
            // TODO: Disable Buttons & show a progress bar
            val selectiveJsonObject = SelectiveJsonObject.Builder()
                .add("first_name", edit_text_user_first_name, user.first_name, false)
                .add("last_name", edit_text_user_last_name, user.last_name, false)
                .add("username", edit_text_username, user.username, true)
                .add("email", edit_text_email_address, user.email, true)
                .build()

            if (selectiveJsonObject != null) {
                accountViewModel.updateUser(selectiveJsonObject).observe(this, Observer {
                    if (it != null) {
                        if (it) {
                            activity?._toastLong("Successfully updated account info")
                            if (selectiveJsonObject.has("username")) {
                                setNavDrawerHeaderTitle(edit_text_username.text.toString())
                            }
                            if (selectiveJsonObject.has("email")) {
                                setNavDrawerHeaderSubTitle(edit_text_email_address.text.toString())
                            }
                            navController._navigateAndPopUpTo(
                                Bundle(), R.id.nav_dashboard, true, R.id.nav_dashboard
                            )
                        }
                        else {
                            // TODO: Enable Buttons & disable progress bar
                            activity?._toastShort("Failed to update account info")
                        }
                    } else {
                        Log.d(TAG, "Update User came back null")
                        // TODO: Enable Buttons & disable progress bar
                    }
                })
            } else {
                // TODO: Enable Buttons & disable progress bar
                activity?._toastShort("Nothing to update")
            }
        }

        fab_delete_user.setOnClickListener {
            // TODO: Disable Buttons & show a progress bar
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
                                // TODO: Enable Buttons & disable progress bar
                                activity?._toastShort("Failed to delete your account")
                            }
                        } else {
                            Log.d(TAG, "Delete User came back null")
                            // TODO: Enable Buttons & disable progress bar
                        }
                    })
                }

                .setNegativeButton("No") { _, _ -> }
                .create()
                .show()
        }
    }

    override fun onDestroy() {
        // TODO: Enable Buttons & disable progress bar
        super.onDestroy()
    }

    private fun loadUserInfoToEditTextFields(user: UserD.UserE) {
        edit_text_user_first_name.setText(user.first_name)
        edit_text_user_last_name.setText(user.last_name)
        edit_text_username.setText(user.username)
        edit_text_email_address.setText(user.email)
    }
}
