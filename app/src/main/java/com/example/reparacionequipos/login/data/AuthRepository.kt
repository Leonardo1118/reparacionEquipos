package com.example.reparacionequipos.login.data

import android.util.Log
import com.example.reparacionequipos.admin.UserInfo
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

    suspend fun getUserInfo(uid: String): Result<UserInfo> {
        return try {
            val snapshot = FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .get()
                .await()

            val rol = snapshot.getString("rol")
            val email = snapshot.getString("email")
            val nombre = snapshot.getString("nombre")

            if (rol != null && email != null && nombre != null) {
                Result.success(UserInfo(rol, email, nombre))
            } else {
                Result.failure(Exception("Rol, email o nombre no definidos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }




}