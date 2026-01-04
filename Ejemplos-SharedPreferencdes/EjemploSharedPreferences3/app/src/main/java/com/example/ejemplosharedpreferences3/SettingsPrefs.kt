package com.example.ejemplosharedpreferences3

import android.content.Context
import android.content.SharedPreferences

class SettingsPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)

    fun saveSettings(name: String, email: String, notifications: Boolean, dark: Boolean) {
        prefs.edit()
            .putString("name", name)
            .putString("email", email)
            .putBoolean("notifications", notifications)
            .putBoolean("dark_mode", dark)
            .apply()
    }

    fun getName(): String = prefs.getString("name", "") ?: ""
    fun getEmail(): String = prefs.getString("email", "") ?: ""
    fun getNotifications(): Boolean = prefs.getBoolean("notifications", false)
    fun getDarkMode(): Boolean = prefs.getBoolean("dark_mode", false)
}
