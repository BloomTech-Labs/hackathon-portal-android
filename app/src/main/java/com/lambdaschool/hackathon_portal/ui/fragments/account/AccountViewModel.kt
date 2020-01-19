package com.lambdaschool.hackathon_portal.ui.fragments.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class AccountViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    fun getUserObjectFromRepository(): User =
        repo.getUserObject()

    fun updateUser(jsonObject: JsonObject): LiveData<Boolean> =
        repo.updateUser(jsonObject)

    fun deleteUser(): LiveData<Boolean> =
        repo.deleteUser()
}