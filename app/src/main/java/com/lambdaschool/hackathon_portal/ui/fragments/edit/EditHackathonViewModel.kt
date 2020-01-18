package com.lambdaschool.hackathon_portal.ui.fragments.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class EditHackathonViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    fun getHackathon(hackathonId: Int): LiveData<Hackathon> {
        return repo.getHackathon(hackathonId)
    }

    fun updateHackathon(hackathonId: Int, jsonObject: JsonObject): LiveData<Hackathon> {
        return repo.updateHackathon(hackathonId, jsonObject)
    }

    fun deleteHackathon(hackathonId: Int): LiveData<Boolean> {
        return repo.deleteHackathon(hackathonId)
    }
}