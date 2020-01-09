package com.lambdaschool.hackathon_portal.model

class UserInfo (
    var id: String?,
    var name: String?,
    var pictureURL: String?,
    var email: String?,
    var accessToken: String?
)

object CurrentUser {
    val currentUser = UserInfo(
        null,
        null,
        null,
        null,
        null
    )
}

fun wipeCurrentUser() {
    CurrentUser.currentUser.id = null
    CurrentUser.currentUser.name = null
    CurrentUser.currentUser.pictureURL = null
    CurrentUser.currentUser.email = null
    CurrentUser.currentUser.accessToken = null
}