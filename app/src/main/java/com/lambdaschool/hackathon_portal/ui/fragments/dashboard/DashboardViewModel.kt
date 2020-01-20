package com.lambdaschool.hackathon_portal.ui.fragments.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val hackathonRepo: HackathonRepository) : ViewModel() {

    fun getAllHackthons(): LiveData<MutableList<Hackathon>> {
        return hackathonRepo.getAllHackathons()
    }

    fun getAllHackathonsList(): LiveData<MutableList<Hackathon>> {
        return hackathonRepo.getAllHackathonLiveList()
    }
}