package com.lambdaschool.hackathon_portal.ui.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    var currentHackathon = MutableLiveData<Hackathon>()

    fun getHackathon(id: Int): LiveData<Hackathon> {
        return repo.getHackathon(id)
    }

    fun getCurrentUserId(): Int {
        return repo.getUserObject().id
    }

    fun approveProject(projectId: Int): LiveData<Boolean> {
        return repo.approveProject(projectId)
    }

    fun deleteProject(projectId: Int): LiveData<Boolean> {
        return repo.deleteProject(projectId)
    }
}