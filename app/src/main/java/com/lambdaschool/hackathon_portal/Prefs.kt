package com.lambdaschool.hackathon_portal

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    companion object {
        const val HACKATHON_PREFS = "hack_prefs"
    }

    val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(HACKATHON_PREFS, Context.MODE_PRIVATE)
}