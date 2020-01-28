package com.lambdaschool.hackathon_portal.model

typealias Project = ProjectModel.Project
typealias Participant = ProjectModel.Participant

sealed class ProjectModel {

    data class Project(
        val id: Int? = null,
        var title: String,
        var description: String,
        var is_approved: Boolean,
        val creator_id: Int,
        val hackathon_id: Int,
        var front_end_spots: Int,
        var back_end_spots: Int,
        var ios_spots: Int,
        var android_spots: Int,
        var data_science_spots: Int,
        var ux_spots: Int,
        var participants: MutableList<Participant>? = null
    ): ProjectModel() {

        constructor(
            title: String,
            description: String,
            is_approved: Boolean,
            creator_id: Int,
            hackathon_id: Int,
            front_end_spots: Int,
            back_end_spots: Int,
            ios_spots: Int,
            android_spots: Int,
            data_science_spots: Int,
            ux_spots: Int
        ): this(
            null,
            title,
            description,
            is_approved,
            creator_id,
            hackathon_id,
            front_end_spots,
            back_end_spots,
            ios_spots,
            android_spots,
            data_science_spots,
            ux_spots)
    }

    data class Participant(
        val user_id: Int,
        var username: String,
        var user_hackathon_role: String,
        val hackathon_id: Int,
        var developer_role: String
    ): ProjectModel()
}