package com.lambdaschool.hackathon_portal.ui.fragments.projectdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.Project
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class ProjectDetailViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    fun getProject(projectId: Int): LiveData<Project> {
        return repo.getProject(projectId)
    }

    fun joinProject(jsonObject: JsonObject, hackathonId: Int): LiveData<String> {
        return repo.joinHackathon(jsonObject, hackathonId, getCurrentUserId())
    }

    fun getCurrentUserId(): Int {
        return repo.getUserObject().id
    }
}