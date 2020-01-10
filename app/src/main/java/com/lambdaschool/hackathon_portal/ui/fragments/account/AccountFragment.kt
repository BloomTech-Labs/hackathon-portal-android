package com.lambdaschool.hackathon_portal.ui.fragments.account


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

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
}
