package com.lambdaschool.hackathon_portal.ui.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    fun getHackathon(id: Int): LiveData<Hackathon> {
        return repo.getHackathon(id)
    }
}