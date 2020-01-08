package com.lambdaschool.hackathon_portal.model

data class Team(
    val team_id: Int,
    var team_name: String,
    var devs: MutableList<User>
)