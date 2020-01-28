package com.lambdaschool.hackathon_portal.ui.fragments.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.Project
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class CreateProjectViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    fun getUserObject(): User =
        repo.getUserObject()

    fun postProject(project: Project): LiveData<Boolean> =
        repo.postProject(project)
}