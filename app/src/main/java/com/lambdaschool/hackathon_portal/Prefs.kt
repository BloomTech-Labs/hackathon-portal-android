package com.lambdaschool.hackathon_portal

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.result.Credentials
import com.lambdaschool.hackathon_portal.model.Token

class Prefs(context: Context) {

    companion object {
        private const val LOGIN_PREFERENCES = "LoginPreferences"
        private const val LOGIN_ACCESS_TOKEN = "LoginToken"
        private const val LOGIN_ID_TOKEN = "LoginUserId"
        private const val LOGIN_TOKEN_EXPIRATION_SECOND = "TokenExpiration"
        private const val INVALID_STRING = "INVALID"
        private const val INVALID_LONG = -1L
    }

    val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE)

    fun createLoginCredentialEntry(credentials: Credentials) {
        val editor = sharedPrefs.edit()
        var longExpiresAt = 0L
        if (credentials.expiresIn != null) {
            longExpiresAt = (System.currentTimeMillis() / 1000) + (credentials.expiresIn) as Long
        }
        editor.putString(LOGIN_ACCESS_TOKEN, credentials.accessToken)
        editor.putString(LOGIN_ID_TOKEN, credentials.idToken)
        editor.putLong(LOGIN_TOKEN_EXPIRATION_SECOND, longExpiresAt)
        editor.apply()
    }
    fun getLoginCredentials(): Token {
        var accessToken = INVALID_STRING
        var idToken = INVALID_STRING
        var expiresAtSeconds = INVALID_LONG
        sharedPrefs.getString(LOGIN_ACCESS_TOKEN, INVALID_STRING)?.let {
            accessToken = it
        }

        sharedPrefs.getString(LOGIN_ID_TOKEN, INVALID_STRING)?.let {
            idToken = it
        }

        sharedPrefs.getLong(LOGIN_TOKEN_EXPIRATION_SECOND, INVALID_LONG)?.let {
            expiresAtSeconds = it
        }

        return Token(accessToken, expiresAtSeconds, idToken)

    }

    fun deleteLoginCredentials() {
        val editor = sharedPrefs.edit()
        editor.putString(LOGIN_ACCESS_TOKEN, INVALID_STRING)
        editor.putString(LOGIN_ID_TOKEN, INVALID_STRING)
        editor.putLong(LOGIN_TOKEN_EXPIRATION_SECOND, INVALID_LONG)
        editor.apply()
    }
}