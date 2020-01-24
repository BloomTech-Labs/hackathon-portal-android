package com.lambdaschool.hackathon_portal.ui.fragments.addadmin


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment

class AddAdminFragment : BaseFragment() {

    private lateinit var addAdminViewModel: AddAdminViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addAdminViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(AddAdminViewModel::class.java)
        addAdminViewModel.getAllUsers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_admin, container, false)
    }


}
