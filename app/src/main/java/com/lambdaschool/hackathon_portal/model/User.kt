package com.lambdaschool.hackathon_portal.model

data class User(
    var id: Int,
    var first_name: String,
    var last_name: String,
    var username: String,
    var email: String,
    var hackathons: MutableList<UserHackathon>
)