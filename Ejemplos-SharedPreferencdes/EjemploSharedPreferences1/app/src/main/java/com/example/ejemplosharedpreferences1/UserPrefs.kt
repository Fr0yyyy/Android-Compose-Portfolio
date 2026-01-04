package com.example.ejemplosharedpreferences1

import android.content.Context
import android.content.SharedPreferences

class UserPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUsername(username: String) {
        prefs.edit()  // abre el editor
            .putString("username", username) // escribe clave "username"
            .apply()      // guarda de forma asíncrona
    }

    fun getUsername(): String {
        return prefs.getString("username", "") ?: ""
    } // si no existe la clave, devuelve "" (valor por defecto)
}
