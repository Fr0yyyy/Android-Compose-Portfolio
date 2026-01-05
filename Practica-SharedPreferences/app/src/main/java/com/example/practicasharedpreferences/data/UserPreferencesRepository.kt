package com.example.practicasharedpreferences.data

import android.content.Context
import android.content.SharedPreferences
import kotlin.apply

class UserPreferencesRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveSettings(name: String, password: String) {
        prefs.edit()
            .putString("name", name)
            .putString("password", password)
            .apply()
    }

    fun getName(): String = prefs.getString("name", "") ?: ""

    fun getPassword(): String = prefs.getString("password", "") ?: ""
}