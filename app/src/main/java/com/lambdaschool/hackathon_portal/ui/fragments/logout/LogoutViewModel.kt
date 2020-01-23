package com.lambdaschool.hackathon_portal.ui.fragments.logout

import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class LogoutViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    fun performLogout() =
        repo.performLogout()
}