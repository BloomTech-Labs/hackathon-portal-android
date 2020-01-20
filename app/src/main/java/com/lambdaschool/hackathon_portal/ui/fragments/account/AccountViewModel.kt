package com.lambdaschool.hackathon_portal.ui.fragments.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.repository.UserRepository
import javax.inject.Inject

class AccountViewModel @Inject constructor(private val userRepo: UserRepository): ViewModel() {

    fun getUserObjectFromRepository(): User =
        userRepo.getUserObject()

    fun updateUser(jsonObject: JsonObject): LiveData<Boolean> =
        userRepo.updateUserById(jsonObject)

    fun deleteUser(): LiveData<Boolean> =
        userRepo.deleteUserById()
}