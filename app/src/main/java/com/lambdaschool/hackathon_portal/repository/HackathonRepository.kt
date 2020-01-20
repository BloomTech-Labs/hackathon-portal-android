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
                           private val userRepo: UserRepository) {

    private val TAG = "REPOSITORY"

    private var allHackathonList = MutableLiveData<MutableList<Hackathon>>()
    fun getAllHackathonList(): LiveData<MutableList<Hackathon>> =
        allHackathonList
//    fun setAllHackathonList(mutableList: MutableList<Hackathon>) {
//        allHackathonList.value = mutableList
//    }

    fun postHackathon(hackathon: Hackathon): LiveData<Boolean> {
        val addHackathonResponse = MutableLiveData<Boolean>()

        hackathonService.postHackathon(userRepo.getUserAuth0Id(), userRepo.getBearerToken(), hackathon)
            .enqueue(object: Callback<Hackathon> {
                override fun onFailure(call: Call<Hackathon>, t: Throwable) {
                    addHackathonResponse.value = false
                    Log.i(TAG, "Failed to connect to API")
                    Log.i(TAG, t.message.toString())
                }

                override fun onResponse(call: Call<Hackathon>, response: Response<Hackathon>) {
                    if (response.isSuccessful) {
                        addHackathonResponse.value = true
                        Log.i(TAG, "Successfully posted hackathon")
                        val newList = copyUserHackathonList()
                        val newUserHackathon = mapPostedHackathonToUserHackathon(response.body())
                        newList?.add(newUserHackathon)
                        userRepo.setUserHackathonList(newList)
                    } else {
                        addHackathonResponse.value = false
                        Log.i(TAG, "Failed to post hackathon")
                        Log.i(TAG, response.code().toString())
                        Log.i(TAG, response.message().toString())
                    }
                }
            })
        return addHackathonResponse
    }

    fun getHackathonById(hackathonId: Int): LiveData<Hackathon> {
        val getHackathonResponse = MutableLiveData<Hackathon>()

        hackathonService.getHackathon(hackathonId, userRepo.getBearerToken()).enqueue(object: Callback<Hackathon> {
            override fun onFailure(call: Call<Hackathon>, t: Throwable) {
                getHackathonResponse.value = null
                Log.i(TAG, "Failed to connect to API")
                Log.i(TAG, t.message.toString())
            }

            override fun onResponse(call: Call<Hackathon>, response: Response<Hackathon>) {
                if (response.isSuccessful) {
                    getHackathonResponse.value = response.body()
                    Log.i(TAG, "Successfully got hackathon")
                } else {
                    getHackathonResponse.value = null
                    Log.i(TAG, "Failed to get hackathon")
                    Log.i(TAG, response.code().toString())
                    Log.i(TAG, response.message().toString())

                }
            }
        })
        return getHackathonResponse
    }

    fun getAllHackathons(): LiveData<MutableList<Hackathon>> {
        val getAllHackathonsResponse = MutableLiveData<MutableList<Hackathon>>()

        hackathonService.getAllHackathons(userRepo.getBearerToken()).enqueue(object : Callback<MutableList<Hackathon>> {
            override fun onFailure(call: Call<MutableList<Hackathon>>, t: Throwable) {
                getAllHackathonsResponse.value = null
                Log.i(TAG, "Failed to connect to API")
                Log.i(TAG, t.message.toString())
            }

            override fun onResponse(call: Call<MutableList<Hackathon>>, response: Response<MutableList<Hackathon>>) {
                if (response.isSuccessful) {
                    getAllHackathonsResponse.value = response.body()
                    Log.i(TAG, "Successfully got hackathons")
                    response.body()?.let {
                        allHackathonList.value = it
                    }
                } else {
                    getAllHackathonsResponse.value = null
                    Log.i(TAG, "Failed to get hackathons")
                    Log.i(TAG, response.code().toString())
                    Log.i(TAG, response.message().toString())
                }
            }
        })
        return getAllHackathonsResponse
    }

    fun updateHackathonById(hackathonId: Int, jsonObject: JsonObject): LiveData<Hackathon> {
        val updateHackathonResponse = MutableLiveData<Hackathon>()

        hackathonService.updateHackathon(hackathonId, userRepo.getUserAuth0Id(), userRepo.getBearerToken(), jsonObject)
            .enqueue(object: Callback<Hackathon> {
                override fun onFailure(call: Call<Hackathon>, t: Throwable) {
                    updateHackathonResponse.value = null
                    Log.i(TAG, "Failed to connect to API")
                    Log.i(TAG, t.message.toString())
                }

                override fun onResponse(call: Call<Hackathon>, response: Response<Hackathon>) {
                    if (response.isSuccessful) {
                        updateHackathonResponse.value = response.body()
                        Log.i(TAG, "Successfully updated hackathon")
                        val index = getUserHackathonIndexFromListById(hackathonId)
                        if (index != null && index != -1) {
                            val updateHackathon = userRepo.getUserHackathonList().value!![index]
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
                                userRepo.setUserHackathonList(newList)
                            }
                        }
                    }
                    else {
                        updateHackathonResponse.value = null
                        Log.i(TAG, "Failed to updated hackathon")
                        Log.i(TAG, response.code().toString())
                        Log.i(TAG, response.message().toString())
                    }
                }
            })
        return updateHackathonResponse
    }

    fun deleteHackathonById(hackathonId: Int): LiveData<Boolean> {
        val deleteHackathonResponse = MutableLiveData<Boolean>()

        hackathonService.deleteHackathon(hackathonId, userRepo.getUserAuth0Id(), userRepo.getBearerToken())
            .enqueue(object: Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    deleteHackathonResponse.value = false
                    Log.i(TAG, "Failed to connect to API")
                    Log.i(TAG, t.message.toString())
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        deleteHackathonResponse.value = true
                        removeUserHackathonFromListById(hackathonId)
                        Log.i(TAG, "Successfully deleted Hackathon")
                    } else {
                        deleteHackathonResponse.value = false
                        Log.i(TAG, "Failed to delete hackathon")
                        Log.i(TAG, response.code().toString())
                        Log.i(TAG, response.message().toString())
                    }
                }

            })
        return deleteHackathonResponse
    }

    private fun removeUserHackathonFromListById(id: Int) {
        val oldList: MutableList<UserHackathon>? = userRepo.getUserHackathonList().value
        val newList = oldList?.toMutableList()
        userRepo.setUserHackathonList(newList?.filter { it.hackathon_id != id }?.toMutableList())
    }

    private fun getUserHackathonIndexFromListById(id: Int): Int? {
        val oldList = userRepo.getUserHackathonList().value
        var newList = oldList?.toMutableList()
        newList = newList?.filter { it.hackathon_id == id }?.toMutableList()
        return if (newList != null) {
            oldList?.indexOf(newList[0])
        } else {
            null
        }
    }

    private fun copyUserHackathonList(): MutableList<UserHackathon>? {
        val oldList = userRepo.getUserHackathonList().value
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
        userRepo.getUserObject().username?.let { username = it }
        userId = userRepo.getUserObject().id
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