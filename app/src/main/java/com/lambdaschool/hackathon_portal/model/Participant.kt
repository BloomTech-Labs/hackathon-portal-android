package com.lambdaschool.hackathon_portal.model

data class Participant(
    val user_id: Int,
    var username: String,
    var user_hackathon_role: String,
    val hackathon_id: Int,
    var developer_role: String
)