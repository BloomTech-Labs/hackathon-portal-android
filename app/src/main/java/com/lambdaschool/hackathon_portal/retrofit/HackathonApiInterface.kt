package com.lambdaschool.hackathon_portal.retrofit

import com.lambdaschool.hackathon_portal.model.Hackathon
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface HackathonApiInterface {

    @POST("hackathons/u/{id}")
    fun postHackathon(@Header("Authorization") bearerToken: String,
                      @Body hackathon: Hackathon): Call<Hackathon>

}