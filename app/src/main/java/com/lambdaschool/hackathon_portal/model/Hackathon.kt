package com.lambdaschool.hackathon_portal.model

data class Hackathon (var name: String,
                      var description: String,
                      var url: String,
                      var start_date: String,
                      var end_date: String,
                      var location: String,
                      var is_open: Boolean) {
    constructor(
        id: Int? = null,
        name: String,
        description: String,
        url: String,
        start_date: String,
        end_date: String,
        location: String,
        is_open: Boolean,
        organizer_id: Int? = null,
        teams: MutableList<Team>? = null,
        admins: MutableList<User>? = null,
        individual_devs: MutableList<User>? = null
    ): this (name, description, url, start_date, end_date, location, is_open)
}