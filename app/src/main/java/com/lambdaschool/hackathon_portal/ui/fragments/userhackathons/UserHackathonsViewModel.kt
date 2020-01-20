package com.lambdaschool.hackathon_portal.ui.fragments.userhackathons

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.UserHackathon
import com.lambdaschool.hackathon_portal.repository.UserRepository
import javax.inject.Inject

class UserHackathonsViewModel @Inject constructor(private val userRepo: UserRepository): ViewModel() {

    fun getUserHackathonList(): LiveData<MutableList<UserHackathon>> {
        return userRepo.getUserHackathonLiveList()
    }
}