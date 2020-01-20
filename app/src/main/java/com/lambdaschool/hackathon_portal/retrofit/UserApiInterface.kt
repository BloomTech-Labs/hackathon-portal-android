package com.lambdaschool.hackathon_portal.retrofit

import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.Deletion
import com.lambdaschool.hackathon_portal.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserApiInterface {

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int,
                    @Header("Authorization") bearerToken: String): Call<User>

    @PUT("users/{id}")
    fun updateUserById(@Path("id") id: Int,
                       @Header("Authorization") bearerToken: String,
                       @Body user: JsonObject): Call<User>

    @DELETE("users/{id}")
    fun deleteUserById(@Path("id") id: Int,
                       @Header("Authorization") bearerToken: String): Call<Deletion>
}