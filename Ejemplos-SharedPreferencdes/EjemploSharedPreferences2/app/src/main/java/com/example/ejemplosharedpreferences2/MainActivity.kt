package com.example.ejemplosharedpreferences2

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
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) {
                    AppEntry()
                }
            }
        }
    }
}

@Composable
fun AppEntry() {
    val context = LocalContext.current
    val prefs = remember { LoginPrefs(context) }

    var loggedIn by remember { mutableStateOf(false) }

    if (loggedIn) {
        HomeScreen(
            username = prefs.getUsername(),
            onLogout = { loggedIn = false }
        )
    } else {
        LoginScreen(
            prefs = prefs,
            onLoginSuccess = { loggedIn = true }
        )
    }
}

@Composable
fun LoginScreen(prefs: LoginPrefs, onLoginSuccess: () -> Unit) {
    var user by remember { mutableStateOf(prefs.getUsername()) }
    var pass by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Login con SharedPreferences")

        OutlinedTextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para guardar credenciales (registro simple)
        Button(onClick = {
            if (user.isBlank() || pass.isBlank()) {
                message = "Usuario y contraseña no pueden estar vacíos."
            } else {
                prefs.saveCredentials(user, pass)
                message = "Credenciales guardadas."
            }
        }) {
            Text("Guardar credenciales")
        }

        // Botón para intentar login
        Button(onClick = {
            val savedUser = prefs.getUsername()
            val savedPass = prefs.getPassword()

            if (!prefs.hasCredentialsSaved()) {
                message = "No hay credenciales guardadas aún."
            } else if (user == savedUser && pass == savedPass) {
                message = ""
                onLoginSuccess()
            } else {
                message = "Usuario o contraseña incorrectos."
            }
        }) {
            Text("Entrar")
        }

        if (message.isNotEmpty()) {
            Text(message, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun HomeScreen(username: String, onLogout: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Zona privada")
        Text("Bienvenido, $username")

        Button(onClick = onLogout) {
            Text("Cerrar sesión")
        }
    }
}
