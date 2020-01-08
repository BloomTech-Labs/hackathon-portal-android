package com.lambdaschool.hackathon_portal.ui.fragments.dashboard


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lambdaschool.hackathon_portal.App

import com.lambdaschool.hackathon_portal.R

class DashboardFragment : Fragment() {

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
    }
}
