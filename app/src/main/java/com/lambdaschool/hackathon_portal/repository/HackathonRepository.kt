package com.lambdaschool.hackathon_portal.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambdaschool.hackathon_portal.model.CurrentUser
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.retrofit.HackathonApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class HackathonRepository (private val hackathonService: HackathonApiInterface) {

    companion object {
        const val REPO_TAG = "REPOSITORY"
    }

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
}