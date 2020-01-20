package com.lambdaschool.hackathon_portal.repository

import com.lambdaschool.hackathon_portal.retrofit.TeamApiInterface
import javax.inject.Singleton

@Singleton
class TeamRepository(private val teamService: TeamApiInterface,
                     private val repoObjs: RepositoryObjects) {

    private val TAG = "TEAM REPO"

    private fun getUserAuth0Id(): Int =
        repoObjs.getUserAuth0Id()
    private fun getBearerToken(): String =
        repoObjs.getBearerToken()
}