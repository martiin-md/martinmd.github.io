package com.example.soundstation.Repositorios

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Registrar usuario con email y contraseña
    suspend fun registerUser(email: String, password: String, username: String): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            // Configurar el nombre de usuario
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()
            user?.updateProfile(profileUpdates)?.await()

            true
        } catch (e: Exception) {
            false
        }
    }

    // Iniciar sesión con email y contraseña
    suspend fun loginUser(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    // Cerrar sesión
    fun signOut() {
        auth.signOut()
    }

    // Obtener el usuario actual
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}
