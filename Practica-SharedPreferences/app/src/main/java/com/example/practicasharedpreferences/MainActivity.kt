package com.example.practicasharedpreferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable // Para poder usar composables personalizados
import androidx.navigation.compose.rememberNavController
import com.example.practicasharedpreferences.ui.theme.PracticaSharedPreferencesTheme
import com.example.practicasharedpreferences.ui.view.LogInScreen //Hay que importar el LogInScreen que esta en la carpeta ui.view
import com.example.practicasharedpreferences.ui.view.WelcomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticaSharedPreferencesTheme {
                val navController = rememberNavController() //El rememberNavController() permite que el NavController se recuerde entre las recomposiciones

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) { //Sirve para navegar entre pantallas

                        //Define la dirección "login" y qué Composable mostrar
                        composable("login") {
                            LogInScreen(navController = navController) //
                        }

                        composable("welcome/{username}/{password}") { backStackEntry -> //Nota técnica: los argumentos se pasan como clave-valor, en este caso "username" y "password".
                                                                                                // backStackEntry es el objeto que contiene los argumentos y nos permite acceder a ellos
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            val password = backStackEntry.arguments?.getString("password") ?: ""
                            WelcomeScreen(navController,username, password)
                        }
                    }
                }
            }
        }
    }
}

