package com.example.ejemplosharedpreferences2

import android.content.Context
import android.content.SharedPreferences

class LoginPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    fun saveCredentials(user: String, pass: String) {
        prefs.edit()
            .putString("username", user)
            .putString("password", pass)
            .apply()
    }

    fun getUsername(): String = prefs.getString("username", "") ?: ""
    fun getPassword(): String = prefs.getString("password", "") ?: ""

    fun hasCredentialsSaved(): Boolean =
        getUsername().isNotEmpty() && getPassword().isNotEmpty()
}
