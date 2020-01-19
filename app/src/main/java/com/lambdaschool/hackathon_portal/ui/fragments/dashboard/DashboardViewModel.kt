package com.lambdaschool.hackathon_portal.ui.fragments.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.model.UserD
import com.lambdaschool.hackathon_portal.model.UserHackathon
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val repo: HackathonRepository) : ViewModel() {

    fun getUser(): LiveData<UserD.UserE> {
        return repo.getUser()
    }

    // Leaving this function because it might be necessary later
    fun getUserHackathonList(): LiveData<MutableList<UserHackathon>> {
        return repo.getUserHackathonList()
    }

    fun getAllHackthons(): LiveData<MutableList<Hackathon>> {
        return repo.getAllHackathons()
    }

    fun getAllHackathonsList(): LiveData<MutableList<Hackathon>> {
        return repo.getAllHackathonList()
    }
}