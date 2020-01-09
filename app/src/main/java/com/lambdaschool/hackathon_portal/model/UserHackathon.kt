package com.lambdaschool.hackathon_portal.model

data class UserHackathon(
    var hackathon_name: String,
    var username: String,
    var user_hackathon_role: String,
    var developer_role: String,
    var team_id: Int,
    var team_name: String,
    var user_id: Int,
    var hackathon_id: Int,
    var start_date: String,
    var end_date: String,
    var hackathon_description: String
)