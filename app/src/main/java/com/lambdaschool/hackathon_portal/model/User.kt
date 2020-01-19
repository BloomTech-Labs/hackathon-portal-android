package com.lambdaschool.hackathon_portal.model

sealed class User {

    data class GetUser(
        var id: Int,
        var first_name: String?,
        var last_name: String?,
        var username: String?,
        var email: String,
        var hackathons: MutableList<UserHackathon>
    ): User()

    class Auth0(
        var id: Int,
        var pictureUrl: String,
        var accessToken: String
    ): User()
}