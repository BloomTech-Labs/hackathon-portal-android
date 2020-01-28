package com.lambdaschool.hackathon_portal.model

data class HackathonProject(
    val project_id: Int,
    var project_title: String,
    var project_description: String,
    var front_end_spots: Int,
    var back_end_spots: Int,
    var ios_spots: Int,
    var android_spots: Int,
    var data_science_spots: Int,
    var ux_spots: Int,
    var participants: MutableList<Participant>?
)