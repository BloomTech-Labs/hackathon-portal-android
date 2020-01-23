package com.lambdaschool.hackathon_portal.ui.fragments.projectdetail


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.lambdaschool.hackathon_portal.R
import com.lambdaschool.hackathon_portal.model.Project
import com.lambdaschool.hackathon_portal.ui.fragments.BaseFragment
import com.lambdaschool.hackathon_portal.util.toastShort
import com.lambdaschool.hackathon_portal.viewmodel.ViewModelProviderFactory

class ProjectDetailsFragment : BaseFragment() {

    private lateinit var projectDetailViewModel: ProjectDetailViewModel
    private lateinit var retrievedProject: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectDetailViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(ProjectDetailViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = arguments?.getInt("project_id")

        projectId?.let { id ->
            projectDetailViewModel.getProject(id).observe(this, Observer { project ->
                if (project != null) {
                    Log.i("PROJECT DETAILS", project.title)
                } else activity?.toastShort("Failed to get project")
            })
        }
    }
}
