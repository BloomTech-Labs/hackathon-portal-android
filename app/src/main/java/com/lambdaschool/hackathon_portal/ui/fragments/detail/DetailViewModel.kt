package com.lambdaschool.hackathon_portal.ui.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.di.FragmentScope
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject
import javax.inject.Singleton

class DetailViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    var currentHackathon = MutableLiveData<Hackathon>()

    fun getHackathon(id: Int): LiveData<Hackathon> {
        return repo.getHackathon(id)
    }
}