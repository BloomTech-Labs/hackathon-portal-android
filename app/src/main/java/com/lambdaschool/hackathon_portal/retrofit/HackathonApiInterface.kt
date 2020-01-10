package com.lambdaschool.hackathon_portal.retrofit

import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.model.User
import retrofit2.Call
import retrofit2.http.*

interface HackathonApiInterface {

    @POST("hackathons/u/{id}")
    fun postHackathon(@Path("id") id: Int?,
                      @Header("Authorization") bearerToken: String,
                      @Body hackathon: Hackathon): Call<Hackathon>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int,
                @Header("Authorization") bearerToken: String): Call<User>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id: Int?,
                   @Header("Authorization") bearerToken: String,
                   @Body user: JsonObject): Call<User>
}