package com.lambdaschool.hackathon_portal.ui.fragments.projectdetail


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment

class ProjectDetailsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = arguments?.getInt("project_id")
        Log.i("ProjectDetailsFragment", "ID: $projectId")
    }


}
