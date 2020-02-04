package com.lambdaschool.hackathon_portal.model

typealias Hackathon = HackathonModel.Hackathon
typealias UserHackathon = HackathonModel.UserHackathon
typealias ProjectHackathon = HackathonModel.ProjectHackathon

sealed class HackathonModel {

    /**
     * Endpoint GET /hackathons:
     *
     *     Does not return fields for `teams`, `admins`, `individual_devs`
     *     which is why they must initially be set to null.
     *
     * Endpoint GET /hackathons/{id}:
     *
     *     Does return fields for those listed above, which is why they
     *     are necessary.
     *
     * Endpoint POST /hackathons/u/{id}:
     *
     *     Fields for `id`, `organizer_id`, `teams`, `admins`, `individual_devs`
     *     are not needed when creating a new hackathon which is why they must
     *     initially be set to null.
     * */
    data class Hackathon (
        var id: Int? = null,
        var name: String,
        var description: String,
        var url: String,
        var start_date: String,
        var end_date: String,
        var location: String,
        var is_open: Boolean,
        var organizer_id: Int? = null,
        var projects: MutableList<ProjectHackathon>? = null,
        var admins: MutableList<Admin>? = null,
        var individual_devs: MutableList<User>? = null
    ): HackathonModel() {

        /**
         * Constructor for the POST request
         * */
        constructor(
            name: String,
            description: String,
            url: String,
            start_date: String,
            end_date: String,
            location: String,
            is_open: Boolean
        ): this (null, name, description, url, start_date, end_date, location, is_open)
    }

    /**
     * Endpoint GET /users/u/{id}:
     *
     *   This model gets returned when we query the users
     *   endpoint and is what's used in the UserHackathonsFragment.
     * */
    data class UserHackathon(
        var hackathon_id: Int,
        var hackathon_name: String,
        var user_hackathon_role: String,
        var developer_role: String,
        var start_date: String,
        var end_date: String,
        var hackathon_description: String,
        var project: ProjectUser?
    ): HackathonModel()

    data class ProjectHackathon(
        val project_id: Int,
        var project_title: String,
        var project_description: String,
        var front_end_spots: Int,
        var back_end_spots: Int,
        var ios_spots: Int,
        var android_spots: Int,
        var data_science_spots: Int,
        var ux_spots: Int,
        var participants: MutableList<Participant>?,
        var is_approved: Boolean? = null
    ): HackathonModel()
}