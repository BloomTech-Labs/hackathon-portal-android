package com.lambdaschool.hackathon_portal.model

data class Hackathon (
    val id: Int?,
    var name: String,
    var description: String,
    var url: String,
    var start_date: String,
    var end_date: String,
    var is_open: Int,
    var organizer_id: Int,
    var teams: MutableList<Team>,
    var admins: MutableList<User>,
    var individual_devs: MutableList<User>
)