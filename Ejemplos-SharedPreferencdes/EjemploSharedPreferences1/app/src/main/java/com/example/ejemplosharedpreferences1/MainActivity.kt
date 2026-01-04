package com.example.ejemplosharedpreferences1

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
                Surface(modifier = Modifier.fillMaxSize()) {
                    UserPreferencesScreen()
                }
            }
        }
    }
}

@Composable
fun UserPreferencesScreen() {
    val context = LocalContext.current
    val userPrefs = remember { UserPrefs(context) }

    // Cargar valor inicial de SharedPreferences
    var username by remember {
        mutableStateOf(userPrefs.getUsername())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "SharedPreferences + Jetpack Compose")

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                userPrefs.saveUsername(username)
            }
        ) {
            Text("Guardar en SharedPreferences")
        }

        if (username.isNotBlank()) {
            Text(text = "Bienvenido, $username")
        } else {
            Text(text = "No hay usuario guardado todavía")
        }
    }
}
