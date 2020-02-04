package com.lambdaschool.hackathon_portal.retrofit

import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.Deletion
import com.lambdaschool.hackathon_portal.model.Hackathon
import com.lambdaschool.hackathon_portal.model.Project
import com.lambdaschool.hackathon_portal.model.User
import retrofit2.Call
import retrofit2.http.*

interface HackathonApiInterface {

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int,
                @Header("Authorization") bearerToken: String): Call<User>

    @GET("users")
    fun getAllUsers(@Header("Authorization") bearerToken: String): Call<MutableList<User>>

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

    @GET("projects/{project_id}")
    fun getProject(@Path("project_id") project_id: Int,
                   @Header("Authorization") bearerToken: String): Call<Project>

    @POST("hackathons/{hack_id}/join/{user_id}")
    fun joinHackathon(@Path("hack_id") hackathonId: Int,
                      @Path("user_id") userId: Int,
                      @Header("Authorization") bearerToken: String,
                      @Body jsonObject: JsonObject): Call<JsonObject>

    @POST("projects")
    fun postProject(@Header("Authorization") bearerToken: String,
                    @Body project: Project): Call<JsonObject>

    @PUT("projects/{project_id}")
    fun approveProject(@Header("Authorization") bearerToken: String,
                       @Path("project_id") projectId: Int,
                       @Body jsonObject: JsonObject): Call<Project>

    @DELETE("projects/{project_id}")
    fun deleteProject(@Header("Authorization") bearerToken: String,
                      @Path("project_id") projectId: Int): Call<JsonObject>
}