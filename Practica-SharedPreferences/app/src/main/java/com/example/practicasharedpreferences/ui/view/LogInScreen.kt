package com.example.practicasharedpreferences.ui.view

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practicasharedpreferences.data.UserPreferencesRepository
import com.example.practicasharedpreferences.viewmodel.LoginViewModel




// Esta Factory es un "manual de instrucciones" que le dice a Android cómo crear un ViewModel
// que tiene dependencias en su constructor (en este caso, un Repository).
class LoginViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {

    // Define una función genérica. 'T' es un comodín para cualquier clase que herede de ViewModel.
    // Recibe el 'plano' (Class) del ViewModel que Android necesita crear.
    // Promete devolver una instancia de ese tipo 'T'.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // isAssignableFrom comprueba si la clase que nos piden (modelClass)
        // es LoginViewModel o una clase padre. Así confirmamos que sabemos construirla.
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            // 1. Creamos la dependencia que necesita nuestro ViewModel.
            val repository = UserPreferencesRepository(application.applicationContext)

            // @Suppress le dice al compilador que ignore la advertencia de "moldeado no seguro".
            // Lo hacemos porque nuestro 'if' ya garantiza que el moldeado ('as T') es seguro.
            @Suppress("UNCHECKED_CAST")
            // 2. Creamos nuestro ViewModel y lo "moldeamos" al tipo genérico T que la función espera.
            return LoginViewModel(repository) as T
        }

        // Si nos piden un ViewModel que no sabemos crear, lanzamos un error.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen() {

    //Necesario el contexto de la aplicación para la LoginViewModelFactory
    val application = LocalContext.current.applicationContext as Application
    
    // Se usa la factory para crear el ViewModel personalizado (porque necesita el contexto del repo)
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(application))

    //Obtiene el estado de la UI desde el ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Inicio de Sesión", style = MaterialTheme.typography.headlineMedium)
            
            TextField(
                value = uiState.username,
                onValueChange = { newUsername -> viewModel.onUsernameChange(newUsername) },
                label = { Text("Nombre de Usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = uiState.password,
                onValueChange = { newPassword -> viewModel.onPasswordChange(newPassword) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { viewModel.saveCredentials() }) {
                    Text("Guardar Preferencias")
                }

                Button(onClick = { /* Lógica para navegar a la siguiente pantalla */ }) {
                    Text("Entrar")
                }
            }
        }
    }
}