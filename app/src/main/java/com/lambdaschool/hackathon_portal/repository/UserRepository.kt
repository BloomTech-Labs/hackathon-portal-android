package com.lambdaschool.hackathon_portal.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.Deletion
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserAuth0
import com.lambdaschool.hackathon_portal.model.UserHackathon
import com.lambdaschool.hackathon_portal.retrofit.HackathonApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class UserRepository(private val hackathonService: HackathonApiInterface,
                     private val userAuth0: UserAuth0,
                     private val user: User) {

    private val TAG = "USER REPO"

    private var userHackathonList = MutableLiveData<MutableList<UserHackathon>>()
    fun getUserHackathonList(): LiveData<MutableList<UserHackathon>> =
        userHackathonList
    fun setUserHackathonList(mutableList: MutableList<UserHackathon>?) {
        userHackathonList.value = mutableList
    }

    /**
     * Have to break the setters for userAuth0 into individual fields because the Auth0
     * claims response requires us to use `let` on each field since it is potentially nullable.
     *
     * Separate methods allows for setting right then and there, in the `let` lambda
     * instead of dealing with temporary variables and calling a single method.
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

    fun getUser(): LiveData<User> {
        val getUserResponse = MutableLiveData<User>()

        hackathonService.getUser(userAuth0.id, bearerToken).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                getUserResponse.value = null
                Log.i(TAG, "Failed to connect to API")
                Log.i(TAG, t.message.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    getUserResponse.value = response.body()
                    Log.i(TAG, "Successfully get user")
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
                    response.body()?.hackathons?.let {
                        user.hackathons = it
                        userHackathonList.value = it
                    }
                }
                else {
                    getUserResponse.value = null
                    Log.i(TAG, "Failed to get user")
                    Log.i(TAG, response.code().toString())
                    Log.i(TAG, response.message().toString())
                }
            }
        })
        return getUserResponse
    }

    fun updateUser(jsonObject: JsonObject): LiveData<Boolean> {
        val updateUserResponse = MutableLiveData<Boolean>()

        hackathonService.updateUser(userAuth0.id, bearerToken, jsonObject)
            .enqueue(object: Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    updateUserResponse.value = false
                    Log.i(TAG, "Failed to connect to API")
                    Log.i(TAG, t.message.toString())
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        updateUserResponse.value = true
                        Log.i(TAG, "Successfully updated user")
                        response.body()?.username?.let {
                            user.username = it
                        }
                        response.body()?.first_name?.let {
                            user.first_name = it
                        }
                        response.body()?.last_name?.let {
                            user.last_name = it
                        }
                        response.body()?.email?.let {
                            user.email = it
                        }
                    } else {
                        updateUserResponse.value = false
                        Log.i(TAG, "Failed to update user")
                        Log.i(TAG, response.code().toString())
                        Log.i(TAG, response.message().toString())

                    }
                }
            })
        return updateUserResponse
    }

    fun deleteUser(): LiveData<Boolean> {
        val deleteUserResponse = MutableLiveData<Boolean>()

        hackathonService.deleteUser(userAuth0.id, bearerToken)
            .enqueue(object: Callback<Deletion> {
                override fun onFailure(call: Call<Deletion>, t: Throwable) {
                    deleteUserResponse.value = false
                    Log.i(TAG, "Failed to connect to API")
                    Log.i(TAG, t.message.toString())
                }

                override fun onResponse(call: Call<Deletion>, response: Response<Deletion>) {
                    if (response.isSuccessful) {
                        deleteUserResponse.value = true
                        Log.i(TAG, "Successfully deleted User")
                    } else {
                        deleteUserResponse.value = false
                        Log.i(TAG, "Failed to delete User")
                        Log.i(TAG, response.code().toString())
                        Log.i(TAG, response.message().toString())

                    }
                }
            })
        return deleteUserResponse
    }
}