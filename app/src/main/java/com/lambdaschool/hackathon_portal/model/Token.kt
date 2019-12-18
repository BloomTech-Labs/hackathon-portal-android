package com.lambdaschool.hackathon_portal.model

data class Token(val accessToken: String, val expiresAtSeconds: Long, val idToken: String)