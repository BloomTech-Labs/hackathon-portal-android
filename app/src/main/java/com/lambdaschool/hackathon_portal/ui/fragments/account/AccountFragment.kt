package com.lambdaschool.hackathon_portal.ui.fragments.account


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.LoggedInUser
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
