package com.lambdaschool.hackathon_portal.ui.fragments.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.LoggedInUser
import com.lambdaschool.hackathon_portal.model.SelectiveJsonObjectBuilder
import com.lambdaschool.hackathon_portal.ui.fragments.NavDrawerFragment
import com.lambdaschool.hackathon_portal.util._navigateAndPopUpTo
import com.lambdaschool.hackathon_portal.util._selectiveJsonObjectBuilder
import com.lambdaschool.hackathon_portal.util._toast
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

            val list: List<SelectiveJsonObjectBuilder> =
                listOf(
                    SelectiveJsonObjectBuilder(
                        "first_name",
                        LoggedInUser.user.first_name,
                        edit_text_user_first_name,
                        false
                    ),
                    SelectiveJsonObjectBuilder(
                        "last_name",
                        LoggedInUser.user.last_name,
                        edit_text_user_last_name,
                        false
                    ),
                    SelectiveJsonObjectBuilder(
                        "username",
                        LoggedInUser.user.username,
                        edit_text_username,
                        true
                    ),
                    SelectiveJsonObjectBuilder(
                        "email",
                        LoggedInUser.user.email,
                        edit_text_email_address,
                        true
                    )
                )
            val jsonObject = _selectiveJsonObjectBuilder(list)

            if (jsonObject != null) {
                accountViewModel.updateUser(jsonObject).observe(this, Observer {
                    if (it != null) {
                        if (it) {
                            activity?._toast("Successfully updated account info")
                            navHeaderTitleTextView.text = edit_text_username.text.toString()
                            navHeaderSubtitleTextView.text = edit_text_email_address.text.toString()
                            navController._navigateAndPopUpTo(Bundle(), R.id.nav_dashboard, true, R.id.nav_dashboard)
                        }
                        else {
                            activity?._toast("Failed to update account info")
                        }
                    } else {
                        activity?._toast("Nothing to update")
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
                                navController.navigate(R.id.nav_logout)
                            } else {
                                activity?._toast("Failed to delete your account")
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
}
