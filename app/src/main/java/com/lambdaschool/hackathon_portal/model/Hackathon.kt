package com.lambdaschool.hackathon_portal.model

data class Hackathon (var id: Int? = null,
                      var name: String,
                      var description: String,
                      var url: String,
                      var start_date: String,
                      var end_date: String,
                      var location: String,
                      var is_open: Boolean,
                      var organizer_id: Int? = null,
                      var teams: MutableList<Team>? = null,
                      var admins: MutableList<Admin>? = null,
                      var individual_devs: MutableList<UserD.UserE>? = null) {
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
