package com.lambdaschool.hackathon_portal.ui.fragments.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserHackathon
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import java.util.*
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val repo: HackathonRepository) : ViewModel() {

    fun getUser(): LiveData<User> {
        return repo.getUser()
    }

    // Leaving this function because it might be necessary later
    fun getUserHackathonList(): LiveData<MutableList<UserHackathon>> {
        return repo.getUserHackathonList()
    }

    fun getAllHackthons(): LiveData<MutableList<Hackathon>> {
        return repo.getAllHackathons()
    }

    fun getAllHackathonsList(): LiveData<MutableList<Hackathon>> {
        return repo.getAllHackathonList()
    }

    fun searchHackathonList(query: String?, oldList: MutableList<Hackathon>,
                       newList: MutableList<Hackathon>): MutableList<Hackathon> {
        newList.clear()
        oldList.forEach {hackathon ->
            when {
                hackathon.name.toLowerCase(Locale.getDefault()).contains(query.toString()
                    .toLowerCase(Locale.getDefault())) ->
                    newList.add(hackathon)
                hackathon.location.toLowerCase(Locale.getDefault()).contains(query.toString()
                    .toLowerCase(Locale.getDefault())) ->
                    newList.add(hackathon)
                hackathon.start_date.toLowerCase(Locale.getDefault()).contains(query.toString()
                    .toLowerCase(Locale.getDefault())) ->
                    newList.add(hackathon)
            }
        }
        return newList
    }
}