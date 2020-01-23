package com.lambdaschool.hackathon_portal.model

data class UserProject(
    val user_id: Int,
    val project_id: Int,
    var title: String,
    var description: String
)