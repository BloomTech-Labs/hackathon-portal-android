package com.lambdaschool.hackathon_portal.ui.fragments.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserHackathon
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import com.lambdaschool.hackathon_portal.repository.UserRepository
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val hackathonRepo: HackathonRepository,
                                             private val userRepo: UserRepository) : ViewModel() {

    fun getUser(): LiveData<User> {
        return userRepo.getUser()
    }

    // Leaving this function because it might be necessary later
    fun getUserHackathonList(): LiveData<MutableList<UserHackathon>> {
        return userRepo.getUserHackathonList()
    }

    fun getAllHackthons(): LiveData<MutableList<Hackathon>> {
        return hackathonRepo.getAllHackathons()
    }

    fun getAllHackathonsList(): LiveData<MutableList<Hackathon>> {
        return hackathonRepo.getAllHackathonList()
    }
}