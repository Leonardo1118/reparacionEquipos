package com.example.reparacionequipos.login.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
  //funcion para obtener el uid del usuaro en sesion
    fun getCurrentUserUid(): String? {
        return auth.currentUser?.uid
    }

    suspend fun getUserRole(uid: String): Result<String> {
        return try {
            val snapshot = FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .get()
                .await()

            val rol = snapshot.getString("rol")
            if (rol != null) {
                Result.success(rol)
            } else {
                Result.failure(Exception("El rol no está definido"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



}