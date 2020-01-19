package com.lambdaschool.hackathon_portal.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.*
import com.lambdaschool.hackathon_portal.retrofit.HackathonApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class HackathonRepository (private val hackathonService: HackathonApiInterface,
                           private val userAuth0: UserD.Auth0,
                           private val user: UserD.UserE) {

    companion object {
        const val REPO_TAG = "REPOSITORY"
    }

    private var userHackathonList = MutableLiveData<MutableList<UserHackathon>>()
    private var allHackathonList = MutableLiveData<MutableList<Hackathon>>()

    fun postHackathon(hackathon: Hackathon): LiveData<Boolean> {
        val addHackathonResponse = MutableLiveData<Boolean>()
        val bearerToken = "Bearer ${CurrentUser.currentUser.accessToken}"
        hackathonService.postHackathon(CurrentUser.currentUser.id?.toInt(), bearerToken, hackathon)
            .enqueue(object: Callback<Hackathon> {
                override fun onFailure(call: Call<Hackathon>, t: Throwable) {
                    addHackathonResponse.value = false
                    Log.i(REPO_TAG, "Failed to connect to API")
                    Log.i(REPO_TAG, t.message.toString())
                }

                override fun onResponse(call: Call<Hackathon>, response: Response<Hackathon>) {
                    if (response.isSuccessful) {
                        addHackathonResponse.value = true
                        Log.i(REPO_TAG, "Successfully posted hackathon")
                        val newList = copyUserHackathonList()
                        val newUserHackathon = mapPostedHackathonToUserHackathon(response.body())
                        newList?.add(newUserHackathon)
                        userHackathonList.value = newList
                    } else {
                        addHackathonResponse.value = false
                        Log.i(REPO_TAG, "Failed to post hackathon")
                        Log.i(REPO_TAG, response.code().toString())
                        Log.i(REPO_TAG, response.message().toString())
                    }
                }
            })
        return addHackathonResponse
    }

    fun getHackathon(id: Int): LiveData<Hackathon> {
        val getHackathonResponse = MutableLiveData<Hackathon>()
        val bearerToken = "Bearer ${CurrentUser.currentUser.accessToken}"
        hackathonService.getHackathon(id, bearerToken).enqueue(object: Callback<Hackathon> {
            override fun onFailure(call: Call<Hackathon>, t: Throwable) {
                getHackathonResponse.value = null
                Log.i(REPO_TAG, "Failed to connect to API")
                Log.i(REPO_TAG, t.message.toString())
            }

            override fun onResponse(call: Call<Hackathon>, response: Response<Hackathon>) {
                if (response.isSuccessful) {
                    getHackathonResponse.value = response.body()
                    Log.i(REPO_TAG, "Successfully got hackathon")
                } else {
                    getHackathonResponse.value = null
                    Log.i(REPO_TAG, "Failed to get hackathon")
                    Log.i(REPO_TAG, response.code().toString())
                    Log.i(REPO_TAG, response.message().toString())

                }
            }
        })
        return getHackathonResponse
    }

    fun getAllHackathons(): LiveData<MutableList<Hackathon>> {
        val getAllHackathonsResponse = MutableLiveData<MutableList<Hackathon>>()
        val bearerToken = "Bearer ${CurrentUser.currentUser.accessToken}"
        hackathonService.getAllHackathons(bearerToken).enqueue(object : Callback<MutableList<Hackathon>> {
            override fun onFailure(call: Call<MutableList<Hackathon>>, t: Throwable) {
                getAllHackathonsResponse.value = null
                Log.i(REPO_TAG, "Failed to connect to API")
                Log.i(REPO_TAG, t.message.toString())
            }

            override fun onResponse(call: Call<MutableList<Hackathon>>, response: Response<MutableList<Hackathon>>) {
                if (response.isSuccessful) {
                    getAllHackathonsResponse.value = response.body()
                    Log.i(REPO_TAG, "Successfully got hackathons")
                    response.body()?.let {
                        allHackathonList.value = it
                    }
                } else {
                    getAllHackathonsResponse.value = null
                    Log.i(REPO_TAG, "Failed to get hackathons")
                    Log.i(REPO_TAG, response.code().toString())
                    Log.i(REPO_TAG, response.message().toString())
                }
            }

        })
        return getAllHackathonsResponse
    }

    fun updateHackathon(hackathonId: Int, jsonObject: JsonObject): LiveData<Hackathon> {
        val updateHackathonResponse = MutableLiveData<Hackathon>()
        val bearerToken = "Bearer ${CurrentUser.currentUser.accessToken}"
        val currentUserId = LoggedInUser.user.id
        hackathonService.updateHackathon(hackathonId, currentUserId, bearerToken, jsonObject)
            .enqueue(object: Callback<Hackathon> {
                override fun onFailure(call: Call<Hackathon>, t: Throwable) {
                    updateHackathonResponse.value = null
                    Log.i(REPO_TAG, "Failed to connect to API")
                    Log.i(REPO_TAG, t.message.toString())
                }

                override fun onResponse(call: Call<Hackathon>, response: Response<Hackathon>) {
                    if (response.isSuccessful) {
                        updateHackathonResponse.value = response.body()
                        Log.i(REPO_TAG, "Successfully updated hackathon")
                        val index = getUserHackathonIndexFromListById(hackathonId)
                        if (index != null && index != -1) {
                            var updateHackathon = userHackathonList.value!![index]
                            response.body()?.name?.let {
                                updateHackathon.hackathon_name = it
                            }
                            response.body()?.description?.let {
                                updateHackathon.hackathon_description = it
                            }
                            response.body()?.id?.let {
                                updateHackathon.hackathon_id = it
                            }
                            response.body()?.start_date?.let {
                                updateHackathon.start_date = it
                            }
                            response.body()?.end_date?.let {
                                updateHackathon.end_date = it
                            }
                            val newList = copyUserHackathonList()
                            if (newList != null) {
                                newList[index] = updateHackathon
                                userHackathonList.value = newList
                            }
                        }
                    }
                    else {
                        updateHackathonResponse.value = null
                        Log.i(REPO_TAG, "Failed to updated hackathon")
                        Log.i(REPO_TAG, response.code().toString())
                        Log.i(REPO_TAG, response.message().toString())
                    }
                }
            })
        return updateHackathonResponse
    }

    fun deleteHackathon(hackathonId: Int): LiveData<Boolean> {
        val deleteHackathonResponse = MutableLiveData<Boolean>()
        val bearerToken = "Bearer ${CurrentUser.currentUser.accessToken}"
        val currentUserId = LoggedInUser.user.id
        hackathonService.deleteHackathon(hackathonId, currentUserId, bearerToken)
            .enqueue(object: Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    deleteHackathonResponse.value = false
                    Log.i(REPO_TAG, "Failed to connect to API")
                    Log.i(REPO_TAG, t.message.toString())
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        deleteHackathonResponse.value = true
                        removeUserHackathonFromListById(hackathonId)
                        Log.i(REPO_TAG, "Successfully deleted Hackathon")
                    } else {
                        deleteHackathonResponse.value = false
                        Log.i(REPO_TAG, "Failed to delete hackathon")
                        Log.i(REPO_TAG, response.code().toString())
                        Log.i(REPO_TAG, response.message().toString())
                    }
                }

            })
        return deleteHackathonResponse
    }

    fun getUser(id: Int): LiveData<User> {
        val getUserResponse = MutableLiveData<User>()
        val bearerToken = "Bearer ${CurrentUser.currentUser.accessToken}"
        hackathonService.getUser(id, bearerToken).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                getUserResponse.value = null
                Log.i(REPO_TAG, "Failed to connect to API")
                Log.i(REPO_TAG, t.message.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    getUserResponse.value = response.body()
                    Log.i(REPO_TAG, "Successfully get user")
                    response.body()?.id?.let {
                        LoggedInUser.user.id = it
                    }
                    response.body()?.username?.let {
                        LoggedInUser.user.username = it
                    }
                    response.body()?.first_name?.let {
                        LoggedInUser.user.first_name = it
                    }
                    response.body()?.last_name?.let {
                        LoggedInUser.user.last_name = it
                    }
                    response.body()?.email?.let {
                        LoggedInUser.user.email = it
                    }
                    response.body()?.hackathons?.let {
                        LoggedInUser.user.hackathons = it
                        userHackathonList.value = it
                    }
                }
                else {
                    getUserResponse.value = null
                    Log.i(REPO_TAG, "Failed to get user")
                    Log.i(REPO_TAG, response.code().toString())
                    Log.i(REPO_TAG, response.message().toString())
                }
            }
        })
        return getUserResponse
    }

    fun updateUser(jsonObject: JsonObject): LiveData<Boolean> {
        val updateUserResponse = MutableLiveData<Boolean>()
        val bearerToken = "Bearer ${CurrentUser.currentUser.accessToken}"
        hackathonService.updateUser(CurrentUser.currentUser.id?.toInt(), bearerToken, jsonObject)
            .enqueue(object: Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    updateUserResponse.value = false
                    Log.i(REPO_TAG, "Failed to connect to API")
                    Log.i(REPO_TAG, t.message.toString())
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        updateUserResponse.value = true
                        Log.i(REPO_TAG, "Successfully updated user")
                        response.body()?.username?.let {
                            LoggedInUser.user.username = it
                            CurrentUser.currentUser.name = it
                        }
                        response.body()?.first_name?.let {
                            LoggedInUser.user.first_name = it
                        }
                        response.body()?.last_name?.let {
                            LoggedInUser.user.last_name = it
                        }
                        response.body()?.email?.let {
                            LoggedInUser.user.email = it
                            CurrentUser.currentUser.email = it
                        }
                    } else {
                        updateUserResponse.value = false
                        Log.i(REPO_TAG, "Failed to update user")
                        Log.i(REPO_TAG, response.code().toString())
                        Log.i(REPO_TAG, response.message().toString())

                    }
                }
            })
        return updateUserResponse
    }

    fun deleteUser(): LiveData<Boolean> {
        val deleteUserResponse = MutableLiveData<Boolean>()
        val bearerToken = "Bearer ${CurrentUser.currentUser.accessToken}"
        hackathonService.deleteUser(CurrentUser.currentUser.id?.toInt(), bearerToken)
            .enqueue(object: Callback<Deletion> {
                override fun onFailure(call: Call<Deletion>, t: Throwable) {
                    deleteUserResponse.value = false
                    Log.i(REPO_TAG, "Failed to connect to API")
                    Log.i(REPO_TAG, t.message.toString())
                }

                override fun onResponse(call: Call<Deletion>, response: Response<Deletion>) {
                    if (response.isSuccessful) {
                        deleteUserResponse.value = true
                        Log.i(REPO_TAG, "Successfully deleted User")
                    } else {
                        deleteUserResponse.value = false
                        Log.i(REPO_TAG, "Failed to delete User")
                        Log.i(REPO_TAG, response.code().toString())
                        Log.i(REPO_TAG, response.message().toString())

                    }
                }
            })
        return deleteUserResponse
    }

    fun getUserHackathonList(): LiveData<MutableList<UserHackathon>> {
        return userHackathonList
    }

    fun getAllHackathonList(): LiveData<MutableList<Hackathon>> {
        return allHackathonList
    }

    private fun removeUserHackathonFromListById(id: Int) {
        val oldList: MutableList<UserHackathon>? = userHackathonList.value
        val newList = oldList?.toMutableList()
        userHackathonList.value = newList?.filter { it.hackathon_id != id }?.toMutableList()
    }

    private fun getUserHackathonIndexFromListById(id: Int): Int? {
        val oldList = userHackathonList.value
        var newList = oldList?.toMutableList()
        newList = newList?.filter { it.hackathon_id == id }?.toMutableList()
        return if (newList != null) {
            oldList?.indexOf(newList[0])
        } else {
            null
        }
    }

    private fun copyUserHackathonList(): MutableList<UserHackathon>? {
        val oldList = userHackathonList.value
        return oldList?.toMutableList()
    }

    private fun mapPostedHackathonToUserHackathon(hackathon: Hackathon?): UserHackathon {
        //this will need to be refactored one judges and hackers are added
        var hackathonName = ""
        var username = ""
        var userHackathonRole = "organizer"
        var developerRole = ""
        var teamId = -1
        var teamName = ""
        var userId = -1
        var hackathonId = -1
        var startDate = ""
        var endDate = ""
        var hackathonDescription = ""

        hackathon?.name?.let { hackathonName = it }
        LoggedInUser.user.username?.let { username = it }
        LoggedInUser.user.id?.let { userId = it }
        hackathon?.id?.let { hackathonId = it }
        hackathon?.start_date?.let { startDate = it }
        hackathon?.end_date?.let { endDate = it }
        hackathon?.description?.let { hackathonDescription = it }

        return UserHackathon(hackathonName,
            username,
            userHackathonRole,
            developerRole,
            teamId,
            teamName,
            userId,
            hackathonId,
            startDate,
            endDate,
            hackathonDescription)
    }
}