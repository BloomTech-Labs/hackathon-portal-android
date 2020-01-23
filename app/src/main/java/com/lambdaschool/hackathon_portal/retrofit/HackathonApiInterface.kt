package com.lambdaschool.hackathon_portal.retrofit

import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.Deletion
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.model.User
import retrofit2.Call
import retrofit2.http.*

interface HackathonApiInterface {

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int,
                @Header("Authorization") bearerToken: String): Call<User>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id: Int,
                   @Header("Authorization") bearerToken: String,
                   @Body user: JsonObject): Call<User>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int,
                   @Header("Authorization") bearerToken: String): Call<Deletion>

    @GET("hackathons")
    fun getAllHackathons(@Header("Authorization") bearerToken: String): Call<MutableList<Hackathon>>

    @GET("hackathons/{id}")
    fun getHackathon(@Path("id") id: Int,
                     @Header("Authorization") bearerToken: String): Call<Hackathon>

    @POST("hackathons/u/{id}")
    fun postHackathon(@Path("id") id: Int,
                      @Header("Authorization") bearerToken: String,
                      @Body hackathon: Hackathon): Call<Hackathon>

    @PUT("hackathons/{hack_id}/u/{org_id}")
    fun updateHackathon(@Path("hack_id") hack_id: Int,
                        @Path("org_id") org_id: Int,
                        @Header("Authorization") bearerToken: String,
                        @Body hackathon: JsonObject): Call<Hackathon>

    @DELETE("hackathons/{hack_id}/u/{org_id}")
    fun deleteHackathon(@Path("hack_id") hack_id: Int,
                        @Path("org_id") org_id: Int,
                        @Header("Authorization") bearerToken: String): Call<Void>
}