package com.example.practicasharedpreferences.viewmodel

import androidx.compose.ui.platform.LocalContext
import com.example.practicasharedpreferences.data.UserPreferencesRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//Data class para almacenar el estado de la UI desde el ViewModel
data class LoginUiState(
    val username: String = "", //Valor por defecto vacío
    val password: String = ""
)

//Necesario pasar el repositorio como parámetro al ViewModel para poder acceder a sus métodos
class LoginViewModel(private val userRepo: UserPreferencesRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState()) //Estado de la UI que se puede actualizar

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow() //Estado de la UI solo observable desde la UI


    init {
        loadInitialCredentials() //Llama a la función para cargar las credenciales iniciales

    }

    private fun loadInitialCredentials() {
        viewModelScope.launch {
            val name = userRepo.getName()
            val password = userRepo.getPassword()
            _uiState.update { currentState ->
                currentState.copy(
                    username = name,
                    password = password
                )
            }
        }
    }

    fun onUsernameChange(username: String) {
        _uiState.update { currentState ->
            currentState.copy(username = username)
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(password = password)
        }
    }

    fun saveCredentials() {
        viewModelScope.launch { //Corrutina que se ejecuta en un hilo separado para no bloquear el hilo principal
            userRepo.SaveSettings(_uiState.value.username, _uiState.value.password)
        }
        
    }

}

