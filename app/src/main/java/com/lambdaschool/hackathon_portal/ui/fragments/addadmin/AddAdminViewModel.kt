package com.lambdaschool.hackathon_portal.ui.fragments.addadmin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserModel
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class AddAdminViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    fun getAllUsers(): LiveData<MutableList<User>> {
        return repo.getAllUser()
    }
}