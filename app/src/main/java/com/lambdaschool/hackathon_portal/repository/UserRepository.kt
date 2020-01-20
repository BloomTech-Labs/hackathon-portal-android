package com.lambdaschool.hackathon_portal.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.Deletion
import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserHackathon
import com.lambdaschool.hackathon_portal.retrofit.UserApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class UserRepository(private val userService: UserApiInterface,
                     private val repoObjs: RepositoryObjects) {

    private val TAG = "USER REPO"

    private fun getUserAuth0Id(): Int =
        repoObjs.getUserAuth0Id()
    private fun getBearerToken(): String =
        repoObjs.getBearerToken()


    // userHackahtonLiveList
    fun getUserHackathonLiveList(): LiveData<MutableList<UserHackathon>> =
        repoObjs.getUserHackathonLiveList()


    // userAuth0
    fun setUserAuth0Id(id: Int) {
        repoObjs.setUserAuth0Id(id)
    }

    fun setUserAuth0PictureUrl(pictureUrl: String) {
        repoObjs.setUserAuth0PictureUrl(pictureUrl)
    }
    fun getUserAuth0PictureUrl(): String =
        repoObjs.getUserAuth0PictureUrl()

    fun setUserAuth0AccessToken(accessToken: String) {
        repoObjs.setUserAuth0AccessToken(accessToken)
    }


    // Logout
    fun performLogout() {
        repoObjs.performLogout()
    }

    fun getUserObject(): User =
        repoObjs.getUserObject()

    fun getUserById(): LiveData<User> {
        val getUserResponse = MutableLiveData<User>()

        userService.getUserById(getUserAuth0Id(), getBearerToken())
            .enqueue(object : Callback<User> { override fun onFailure(call: Call<User>, t: Throwable) {
                getUserResponse.value = null
                Log.i(TAG, "Failed to connect to API")
                Log.i(TAG, t.message.toString())
            }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        getUserResponse.value = response.body()
                        Log.i(TAG, "Successfully get user")

                        repoObjs.setUser_ExceptHackathonsField(response)

                        response.body()?.hackathons?.let {
                            repoObjs.setUserHackathonsAndLiveList(it)
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

    fun updateUserById(jsonObject: JsonObject): LiveData<Boolean> {
        val updateUserResponse = MutableLiveData<Boolean>()

        userService.updateUserById(getUserAuth0Id(), getBearerToken(), jsonObject)
            .enqueue(object: Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    updateUserResponse.value = false
                    Log.i(TAG, "Failed to connect to API")
                    Log.i(TAG, t.message.toString())
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        updateUserResponse.value = true
                        repoObjs.setUser_ExceptHackathonsField(response)
                        Log.i(TAG, "Successfully updated user")
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

    fun deleteUserById(): LiveData<Boolean> {
        val deleteUserResponse = MutableLiveData<Boolean>()

        userService.deleteUserById(getUserAuth0Id(), getBearerToken())
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