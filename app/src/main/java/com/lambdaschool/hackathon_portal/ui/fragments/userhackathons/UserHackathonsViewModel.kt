package com.lambdaschool.hackathon_portal.ui.fragments.userhackathons

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserHackathon
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class UserHackathonsViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    fun getUserHackathonList(): LiveData<MutableList<UserHackathon>> {
        return repo.getUserHackathonList()
    }

    fun getUser(): LiveData<User> {
        return repo.getUser()
    }
}