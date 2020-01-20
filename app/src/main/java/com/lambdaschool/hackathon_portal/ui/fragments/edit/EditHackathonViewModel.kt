package com.lambdaschool.hackathon_portal.ui.fragments.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class EditHackathonViewModel @Inject constructor(private val hackathonRepo: HackathonRepository): ViewModel() {

    fun getHackathon(hackathonId: Int): LiveData<Hackathon> {
        return hackathonRepo.getHackathon(hackathonId)
    }

    fun updateHackathon(hackathonId: Int, jsonObject: JsonObject): LiveData<Hackathon> {
        return hackathonRepo.updateHackathon(hackathonId, jsonObject)
    }

    fun deleteHackathon(hackathonId: Int): LiveData<Boolean> {
        return hackathonRepo.deleteHackathon(hackathonId)
    }
}