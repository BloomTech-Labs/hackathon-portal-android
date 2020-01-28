package com.lambdaschool.hackathon_portal.model

typealias User = UserModel.User
typealias ProjectUser = UserModel.ProjectUser
typealias UserAuth0 = UserModel.Auth0

sealed class UserModel {

    data class User(
        var id: Int,
        var first_name: String?,
        var last_name: String?,
        var username: String?,
        var email: String,
        var hackathons: MutableList<UserHackathon>?
    ): UserModel()

    data class ProjectUser(
        val user_id: Int,
        val project_id: Int,
        var title: String,
        var description: String
    ): UserModel()

    class Auth0(
        var id: Int,
        var pictureUrl: String,
        var accessToken: String
    ): UserModel()
}