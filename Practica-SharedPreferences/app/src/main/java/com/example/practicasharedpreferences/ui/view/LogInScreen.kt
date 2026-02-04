package com.example.practicasharedpreferences.ui.view

import android.app.Application
import androidx.activity.result.launch
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.practicasharedpreferences.data.UserPreferencesRepository
import com.example.practicasharedpreferences.viewmodel.LoginViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(navController: NavController) { //Necesita pasarle el NavController para poder navegar

    //Se crea el ViewModel desde el owner.
    val viewModel: LoginViewModel = viewModel()

    //Obtiene el estado de la UI desde el ViewModel
    val uiState by viewModel.uiState.collectAsState() //Para reaccionar ante los cambios de estado del ViewModel (Usuario -> _uiState -> uiState)

    val scope = rememberCoroutineScope() //Lanzador de tareas en segundo plano


    val snackbarHostState =
        remember { SnackbarHostState() } //remember para que no se pierda el estado del snackbarHostState



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } //Agrega el snackbarHost a la Scaffold con el estado de snackbarHostState para mostrar mensajes de error
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
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

                Button(onClick = {
                    // Se lanza una corrutina para verificar las credenciales
                    scope.launch {
                        if (viewModel.canLogIn()) {
                            // Se construye la ruta con los datos actuales de la UI.
                            navController.navigate("welcome/${uiState.username}/${uiState.password}")
                        } else {

                            snackbarHostState.showSnackbar("Credenciales incorrectas")
                        }
                    }
                }) {
                    Text("Entrar")
                }
            }
        }
    }
}