package com.lambdaschool.hackathon_portal.model

sealed class UserD {

    data class UserE(
        var id: Int,
        var first_name: String?,
        var last_name: String?,
        var username: String?,
        var email: String,
        var hackathons: MutableList<UserHackathon>
    ): UserD()

    class Auth0(
        var id: Int,
        var pictureUrl: String,
        var accessToken: String
    ): UserD()
}