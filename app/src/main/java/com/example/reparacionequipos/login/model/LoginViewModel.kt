package com.example.reparacionequipos.login.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reparacionequipos.login.LoginState
import com.example.reparacionequipos.login.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Correo o contraseña vacíos")
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            val loginResult = repository.login(email, password)
            if (loginResult.isSuccess) {
                _loginState.value = LoginState.Success
                val uid = repository.getCurrentUserUid()
                println("🔥 Recibido uid: $uid") // LOG
                if (uid != null) {
                    val infoResult = repository.getUserInfo(uid)
                    if (infoResult.isSuccess) {
                        val info = infoResult.getOrNull()
                        if (info != null) {
                            _loginState.value = LoginState.LoggedInUser(uid, info.rol, info.nombre , info.email)
                        } else {
                            _loginState.value = LoginState.Error("No se encontró la información del usuario")
                        }
                    } else {
                        _loginState.value = LoginState.Error(infoResult.exceptionOrNull()?.message ?: "Error al obtener el rol")
                    }
                } else {
                    _loginState.value = LoginState.Error("No se pudo obtener el UID")
                }
            } else {
                _loginState.value = LoginState.Error("credenciales invalidas")
            }
        }
    }

}