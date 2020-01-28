package com.lambdaschool.hackathon_portal.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.*
import com.lambdaschool.hackathon_portal.retrofit.HackathonApiInterface
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class HackathonRepository (private val hackathonService: HackathonApiInterface,
                           private val userAuth0: UserAuth0,
                           private val user: User) {

    companion object {
        const val REPO_TAG = "REPOSITORY"
    }

    private var userHackathonList = MutableLiveData<MutableList<UserHackathon>>()
    private var allHackathonList = MutableLiveData<MutableList<Hackathon>>()

    // This will be set upon login and then wiped on logout
    private var bearerToken = ""

    // Have to break these into individual fields because the Auth0 claims response
    // requires us to use `let` on each field since it is potentially nullable.
    // Separate methods allows for setting right then and there, in the `let` lambda
    // instead of dealing with temporary variables and calling a single method.
    fun setUserAuth0Id(id: Int) {
        userAuth0.id = id
    }

    fun setUserAuth0PictureUrl(pictureUrl: String) {
        userAuth0.pictureUrl = pictureUrl
    }

    fun setUserAuth0AccessToken(accessToken: String) {
        userAuth0.accessToken = accessToken
        bearerToken = "Bearer $accessToken"
    }

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

    fun getUserObject(): User {
        return user
    }

    fun getUserAuth0PictureUrl(): String = userAuth0.pictureUrl
    // AccessToken should ONLY be used here in the repo when coding up network calls.

    fun postHackathon(hackathon: Hackathon): LiveData<Boolean> {
        val addHackathonResponse = MutableLiveData<Boolean>()

        hackathonService.postHackathon(userAuth0.id, bearerToken, hackathon)
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

    fun getHackathon(hackathonId: Int): LiveData<Hackathon> {
        val getHackathonResponse = MutableLiveData<Hackathon>()

        hackathonService.getHackathon(hackathonId, bearerToken).enqueue(object: Callback<Hackathon> {
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

        hackathonService.updateHackathon(hackathonId, userAuth0.id, bearerToken, jsonObject)
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
                            val updateHackathon = userHackathonList.value!![index]
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

        hackathonService.deleteHackathon(hackathonId, userAuth0.id, bearerToken)
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

    fun getUser(): LiveData<User> {
        val getUserResponse = MutableLiveData<User>()

        hackathonService.getUser(userAuth0.id, bearerToken).enqueue(object : Callback<User> {
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
                    Log.i(REPO_TAG, "Failed to get user")
                    Log.i(REPO_TAG, response.code().toString())
                    Log.i(REPO_TAG, response.message().toString())
                }
            }
        })
        return getUserResponse
    }

    fun getAllUser(): LiveData<MutableList<User>> {
        val allUsersResponse = MutableLiveData<MutableList<User>>()
        hackathonService.getAllUsers(bearerToken).enqueue(object : Callback<MutableList<User>> {
            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                allUsersResponse.value = null
                Log.i(REPO_TAG, "Failed to connect to API")
                Log.i(REPO_TAG, t.message.toString())
            }

            override fun onResponse(call: Call<MutableList<User>>, response: Response<MutableList<User>>) {
                if (response.isSuccessful) {
                    allUsersResponse.value = response.body()
                    Log.i(REPO_TAG, "Successfully got users, ${response.body()!![0].username}")
                } else {
                    allUsersResponse.value = null
                    Log.i(REPO_TAG, response.message())
                }
            }
        })
        return allUsersResponse
    }

    fun updateUser(jsonObject: JsonObject): LiveData<Boolean> {
        val updateUserResponse = MutableLiveData<Boolean>()

        hackathonService.updateUser(userAuth0.id, bearerToken, jsonObject)
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

        hackathonService.deleteUser(userAuth0.id, bearerToken)
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

    fun getProject(projectId: Int): LiveData<Project> {
        val getProjectResponse = MutableLiveData<Project>()
        hackathonService.getProject(projectId, bearerToken).enqueue(object: Callback<Project>{
            override fun onFailure(call: Call<Project>, t: Throwable) {
                getProjectResponse.value = null
                Log.i(REPO_TAG, "Failed to connect to API")
                Log.i(REPO_TAG, t.message.toString())
            }

            override fun onResponse(call: Call<Project>, response: Response<Project>) {
                 if (response.isSuccessful) {
                     getProjectResponse.value = response.body()
                     Log.i(REPO_TAG, "Successfuly got project")
                 } else {
                     getProjectResponse.value = null
                     Log.i(REPO_TAG, "Failed to get project")
                     Log.i(REPO_TAG, response.code().toString())
                     Log.i(REPO_TAG, response.message().toString())
                 }
            }

        })
        return getProjectResponse
    }

    fun joinHackathon(jsonObject: JsonObject, hackathonId: Int, userId: Int): LiveData<String> {
        val joinHackathonResponse = MutableLiveData<String>()
        Log.i(REPO_TAG, jsonObject.toString())
        hackathonService.joinHackathon(hackathonId, userId, bearerToken, jsonObject)
            .enqueue(object: Callback<JsonObject>{
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    joinHackathonResponse.value = "Failed to connect to API"
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        joinHackathonResponse.value = response.body()?.get("message").toString()
                    } else if (response.code() == 401) {
                        val newJson =  JSONObject(response.errorBody()?.string())
                        joinHackathonResponse.value = newJson.get("error").toString()
                    }
                }
            })
        return joinHackathonResponse
    }

    fun postProject(project: Project): LiveData<Boolean> {
        val addProjectResponse = MutableLiveData<Boolean>()

        hackathonService.postProject(bearerToken, project)
            .enqueue(object: Callback<Project> {
                override fun onFailure(call: Call<Project>, t: Throwable) {
                    addProjectResponse.value = false
                    Log.i(REPO_TAG, "Failed to connect to API")
                    Log.i(REPO_TAG, t.message.toString())
                }

                override fun onResponse(call: Call<Project>, response: Response<Project>) {
                    if (response.isSuccessful) {
                        addProjectResponse.value = true
                        Log.i(REPO_TAG, "Successfully posted hackathon")
                    } else {
                        addProjectResponse.value = false
                        Log.i(REPO_TAG, "Failed to post hackathon")
                        Log.i(REPO_TAG, response.code().toString())
                        Log.i(REPO_TAG, response.message().toString())
                    }
                }
            })
        return addProjectResponse
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
        var userHackathonRole = "organizer"
        var developerRole = ""
        var hackathonId = -1
        var startDate = ""
        var endDate = ""
        var hackathonDescription = ""

        hackathon?.name?.let { hackathonName = it }
        hackathon?.id?.let { hackathonId = it }
        hackathon?.start_date?.let { startDate = it }
        hackathon?.end_date?.let { endDate = it }
        hackathon?.description?.let { hackathonDescription = it }

        return UserHackathon(
            hackathonId,
            hackathonName,
            userHackathonRole,
            developerRole,
            startDate,
            endDate,
            hackathonDescription,
            null)
    }
}