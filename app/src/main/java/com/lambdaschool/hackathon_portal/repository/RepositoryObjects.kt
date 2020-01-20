package com.lambdaschool.hackathon_portal.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserAuth0
import com.lambdaschool.hackathon_portal.model.UserHackathon

/**
 * This is where all of the repository objects live, only accessible through methods for
 * better control over what is accessible to outside.
 * */
class RepositoryObjects(private val userAuth0: UserAuth0,
                        private val user: User) {

    /**
     * User
     * */

    private var userHackathonLiveList = MutableLiveData<MutableList<UserHackathon>>()
    fun getUserHackathonLiveList(): LiveData<MutableList<UserHackathon>> =
        userHackathonLiveList
    fun setUserHackathonLiveList(mutableList: MutableList<UserHackathon>?) {
        userHackathonLiveList.value = mutableList
    }

    /**
     * Have to break the setters for userAuth0 into individual fields because the Auth0
     * claims response requires us to use `let` on each field since it is potentially nullable.
     *
     * Separate methods allow for setting individual fields of userAuth0 right then and there
     * in the `let` lambda instead of dealing with temporary variables to calling a single method
     * with all of the data needing to be passed.
     * */
    fun setUserAuth0Id(id: Int) {
        userAuth0.id = id
    }
    fun getUserAuth0Id(): Int =
        userAuth0.id

    fun setUserAuth0PictureUrl(pictureUrl: String) {
        userAuth0.pictureUrl = pictureUrl
    }
    fun getUserAuth0PictureUrl(): String =
        userAuth0.pictureUrl

    // This will be set upon login and then wiped on logout
    private var bearerToken = ""
    fun setUserAuth0AccessToken(accessToken: String) {
        userAuth0.accessToken = accessToken
        bearerToken = "Bearer $accessToken"
    }
    fun getBearerToken(): String =
        bearerToken

    fun performLogout() {
        // Wipe userAuth0
        userAuth0.id = -1
        userAuth0.pictureUrl = ""
        userAuth0.accessToken = ""

        // Wipe bearerToken
        bearerToken = ""

        // Wipe user
        user.id = -1
        user.first_name = null
        user.last_name = null
        user.username = ""
        user.email = ""
        user.hackathons = mutableListOf()

        // TODO: Should we also clear the mutable objects used in this file?
    }

    fun getUserObject(): User =
        user
}