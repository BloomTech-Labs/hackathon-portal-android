package com.lambdaschool.hackathon_portal.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.repository.HackathonRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repo: HackathonRepository): ViewModel() {

    fun setUserAuth0Id(id: Int) =
        repo.setUserAuth0Id(id)

    fun setUserAuth0PictureUrl(pictureUrl: String) =
        repo.setUserAuth0PictureUrl(pictureUrl)

    fun setUserAuth0AccessToken(accessToken: String) =
        repo.setUserAuth0AccessToken(accessToken)

    fun getUser(): LiveData<User> =
        repo.getUser()

    fun getUserAuth0PictureUrl() =
        repo.getUserAuth0PictureUrl()
}