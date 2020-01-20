package com.lambdaschool.hackathon_portal.ui.fragments.logout

import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import com.lambdaschool.hackathon_portal.repository.UserRepository
import javax.inject.Inject

class LogoutViewModel @Inject constructor(private val userRepo: UserRepository): ViewModel() {

    fun performLogout() =
        userRepo.performLogout()
}