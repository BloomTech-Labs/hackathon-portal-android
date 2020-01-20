package com.lambdaschool.hackathon_portal.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserAuth0
import com.lambdaschool.hackathon_portal.model.UserHackathon
import retrofit2.Response

/**
 * This is where all of the repository objects live, only accessible through methods from
 * the respective repositories.
 * */
class RepositoryObjects(private val userAuth0: UserAuth0,
                        private val user: User) {

    /**
     * User
     * */

    // userHackathonLiveList:
    //   To set, use setUserHackathons method which will also keep the user object current.
    private var userHackathonLiveList = MutableLiveData<MutableList<UserHackathon>>()
    fun getUserHackathonLiveList(): LiveData<MutableList<UserHackathon>> =
        userHackathonLiveList


    // userAuth0
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

    private var bearerToken = ""
    fun setUserAuth0AccessToken(accessToken: String) {
        userAuth0.accessToken = accessToken
        bearerToken = "Bearer $accessToken"
    }
    fun getBearerToken(): String =
        bearerToken


    // user
    fun setUser_ExceptHackathonsField(response: Response<User>) {
        response.body()?.id?.let {
            user.id = it
        }
        response.body()?.first_name?.let {
            user.first_name = it
        }
        response.body()?.last_name?.let {
            user.last_name = it
        }
        response.body()?.username?.let {
            user.username = it
        }
        response.body()?.email?.let {
            user.email = it
        }
    }

    fun setUserHackathonsAndLiveList(hackathons: MutableList<UserHackathon>?) {
        hackathons?.let {
            user.hackathons = it
        }
        userHackathonLiveList.value = hackathons
    }

    fun getUserObject(): User =
        user

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


    /**
     * Hackathon
     * */

    // allHackathonLiveList
    private var allHackathonLiveList = MutableLiveData<MutableList<Hackathon>>()
    fun setAllHackathonLiveList(mutableList: MutableList<Hackathon>) {
        allHackathonLiveList.value = mutableList
    }
    fun getAllHackathonLiveList(): LiveData<MutableList<Hackathon>> =
        allHackathonLiveList
}