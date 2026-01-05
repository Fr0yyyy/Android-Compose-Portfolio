package com.example.practicasharedpreferences.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicasharedpreferences.data.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//Data class para almacenar el estado de la UI desde el ViewModel
data class LoginUiState(
    val username: String = "", //Valor por defecto vacío
    val password: String = ""
)

//Necesario pasar el repositorio como parámetro al ViewModel para poder acceder a sus métodos
class LoginViewModel(application: Application) : AndroidViewModel(application){

    //Repositorio para acceder a las preferencias de usuario.
    private val userRepo: UserPreferencesRepository = UserPreferencesRepository(application)
    //
    private val _uiState =
        MutableStateFlow(LoginUiState()) //Estado de la UI que se puede actualizar

    val uiState: StateFlow<LoginUiState> =
        _uiState.asStateFlow() //Estado de la UI solo observable desde la UI


    init {
        loadInitialCredentials() //Llama a la función para cargar las credenciales iniciales

    }

    private fun loadInitialCredentials() {
        viewModelScope.launch(Dispatchers.IO) { //Dipsatchers.IO para que se ejecute en un hilo separado

            val name = userRepo.getName()
            val password = userRepo.getPassword()
            withContext(Dispatchers.Main) { //Regresa al hilo principal

                _uiState.update { currentState ->
                    currentState.copy(
                        username = name,
                        password = password
                    )
                }
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

            userRepo.saveSettings(_uiState.value.username, _uiState.value.password)
        }

    }

    suspend fun canLogIn(): Boolean { //El suspend es una función que puede ser pausada y reanudada en un futuro, evita que se bloquee la UI

        val savedUserName = userRepo.getName()
        val savedUserPassword = userRepo.getPassword()
        return _uiState.value.username == savedUserName && _uiState.value.password == savedUserPassword
    }


}
