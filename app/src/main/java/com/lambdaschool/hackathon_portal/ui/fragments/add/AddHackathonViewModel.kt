package com.lambdaschool.hackathon_portal.ui.fragments.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.HackathonRepository
import com.lambdaschool.hackathon_portal.model.Hackathon
import javax.inject.Inject

class AddHackathonViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    fun postHackathon(hackathon: Hackathon): LiveData<Boolean> {
        return repo.postHackathon(hackathon)
    }
}