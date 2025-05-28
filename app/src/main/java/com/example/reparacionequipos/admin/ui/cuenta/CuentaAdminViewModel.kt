package com.example.reparacionequipos.admin.ui.cuenta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reparacionequipos.admin.UserInfo
import com.example.reparacionequipos.login.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CuentaAdminViewModel : ViewModel() {
    private val repository: AuthRepository = AuthRepository()
    private val _userInfoState = MutableStateFlow<Result<UserInfo>?>(null)
    val userInfoState: StateFlow<Result<UserInfo>?> = _userInfoState

    fun cargarDatosUsuario() {
        val uid = repository.getCurrentUserUid()
        if (uid != null) {
            viewModelScope.launch {
                val result = repository.getUserInfo(uid)
                _userInfoState.value = result
            }
        } else {
            _userInfoState.value = Result.failure(Exception("Usuario no autenticado"))
        }
    }

}