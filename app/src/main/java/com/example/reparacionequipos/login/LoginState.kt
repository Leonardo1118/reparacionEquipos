package com.example.reparacionequipos.login

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
    data class LoggedInUser(val uid: String, val rol: String, val nombre:String , val email:String) : LoginState()

}