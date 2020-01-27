package com.lambdaschool.hackathon_portal.model

typealias Project = ProjectModel.Project
typealias UserProject = ProjectModel.UserProject
typealias Participant = ProjectModel.Participant
typealias HackathonProject = ProjectModel.HackathonProject

sealed class ProjectModel {

    data class Project(
        val id: Int,
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
        var participants: MutableList<Participant>
    ): ProjectModel()

    data class UserProject(
        val user_id: Int,
        val project_id: Int,
        var title: String,
        var description: String
    ): ProjectModel()

    data class Participant(
        val user_id: Int,
        var username: String,
        var user_hackathon_role: String,
        val hackathon_id: Int,
        var developer_role: String
    ): ProjectModel()

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
    ): ProjectModel()
}