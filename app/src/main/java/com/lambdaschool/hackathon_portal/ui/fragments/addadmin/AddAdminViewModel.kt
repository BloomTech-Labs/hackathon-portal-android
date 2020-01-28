package com.lambdaschool.hackathon_portal.ui.fragments.addadmin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserModel
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class AddAdminViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    fun getAllUsers(): LiveData<MutableList<User>> {
        return repo.getAllUser()
    }

    fun addOrganizerToHackathon(hackathonId: Int, userId: Int, jsonObject: JsonObject): LiveData<String> {
        return repo.joinHackathon(jsonObject, hackathonId, userId)
    }

    fun searchUserList(query: String?, oldList: MutableList<User>, newList: MutableList<User>): MutableList<User> {
        newList.clear()
        oldList.forEach {user ->
            when {
                user.username.toString().contains(query.toString()) -> newList.add(user)
                user.email.contains(query.toString()) -> newList.add(user)
                user.first_name.toString().contains(query.toString()) -> newList.add(user)
                user.last_name.toString().contains(query.toString()) -> newList.add(user)
            }
        }
        return newList
    }
}