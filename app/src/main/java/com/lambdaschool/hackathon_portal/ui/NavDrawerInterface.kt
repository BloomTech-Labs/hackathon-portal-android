package com.lambdaschool.hackathon_portal.ui

interface NavDrawerInterface {
    fun lockDrawer()
    fun unlockDrawer()
    fun setUserImage(imageUrl: String)
    fun setUsername(username: String)
    fun setUserEmail(email: String)
}