package com.lambdaschool.hackathon_portal.repository

import com.lambdaschool.hackathon_portal.model.User
import com.lambdaschool.hackathon_portal.model.UserAuth0

/**
 * This is where all of the repository objects live, only accessible through methods for
 * better control over what is accessible to outside.
 * */
class RepositoryObjects(private val userAuth0: UserAuth0,
                        private val user: User) {

}