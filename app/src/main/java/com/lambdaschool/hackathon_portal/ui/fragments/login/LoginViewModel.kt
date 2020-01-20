package com.lambdaschool.hackathon_portal.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.repository.UserRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepo: UserRepository): ViewModel() {

    fun setUserAuth0Id(id: Int) =
        userRepo.setUserAuth0Id(id)

    fun setUserAuth0PictureUrl(pictureUrl: String) =
        userRepo.setUserAuth0PictureUrl(pictureUrl)

    fun setUserAuth0AccessToken(accessToken: String) =
        userRepo.setUserAuth0AccessToken(accessToken)

    fun getUser(): LiveData<User> =
        userRepo.getUser()

    fun getUserAuth0PictureUrl() =
        userRepo.getUserAuth0PictureUrl()
}