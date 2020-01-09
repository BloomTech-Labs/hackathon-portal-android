package com.lambdaschool.hackathon_portal.model

data class User(
    val user_id: Int,
    var username: String,
    var developer_role: String?,
    var user_hackathon_role: String?
)