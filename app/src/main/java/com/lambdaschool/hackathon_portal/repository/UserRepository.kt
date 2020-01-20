package com.lambdaschool.hackathon_portal.repository

import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserAuth0
import com.lambdaschool.hackathon_portal.retrofit.HackathonApiInterface
import javax.inject.Singleton

@Singleton
class UserRepository(private val hackathonService: HackathonApiInterface,
                     private val userAuth0: UserAuth0,
                     private val user: User) {

}