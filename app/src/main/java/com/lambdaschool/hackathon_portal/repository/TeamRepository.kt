package com.lambdaschool.hackathon_portal.repository

import com.lambdaschool.hackathon_portal.retrofit.HackathonApiInterface
import javax.inject.Singleton

@Singleton
class TeamRepository(private val hackathonService: HackathonApiInterface,
                     private val userRepo: UserRepository) {

}