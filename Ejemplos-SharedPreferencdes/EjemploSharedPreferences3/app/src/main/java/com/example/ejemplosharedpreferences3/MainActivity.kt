package com.example.ejemplosharedpreferences3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val prefs = remember { SettingsPrefs(context) }

            // Podrías aplicar aquí un tema oscuro según prefs.getDarkMode()
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    SettingsScreen(prefs)
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(prefs: SettingsPrefs) {
    var name by remember { mutableStateOf(prefs.getName()) }
    var email by remember { mutableStateOf(prefs.getEmail()) }
    var notifications by remember { mutableStateOf(prefs.getNotifications()) }
    var darkMode by remember { mutableStateOf(prefs.getDarkMode()) }
    var message by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Configuración de usuario (SharedPreferences)")

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Recibir notificaciones")
            Switch(
                checked = notifications,
                onCheckedChange = { notifications = it }
            )
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Tema oscuro")
            Switch(
                checked = darkMode,
                onCheckedChange = { darkMode = it }
            )
        }

        Button(onClick = {
            prefs.saveSettings(name, email, notifications, darkMode)
            message = "Configuración guardada."
        }) {
            Text("Guardar configuración")
        }

        if (message.isNotEmpty()) {
            Text(message, color = MaterialTheme.colorScheme.primary)
        }
    }
}
