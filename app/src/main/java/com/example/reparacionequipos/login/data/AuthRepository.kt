package com.example.reparacionequipos.login.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    suspend fun login(email: String, password: String): Result<Unit> {
        Log.d("AuthDebug", "Intentando iniciar sesión con email: $email")
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Log.d("AuthDebug", "Inicio de sesión exitoso para $email")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AuthDebug", "Error al iniciar sesión", e)
            Result.failure(e)
        }
    }

}