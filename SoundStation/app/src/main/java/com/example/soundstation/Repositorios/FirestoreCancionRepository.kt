package com.example.soundstation.Repositorios
import com.example.soundstation.modelo.Cancion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreCancionRepository {

    private val db = FirebaseFirestore.getInstance()
    private val coleccionCanciones = db.collection("canciones")

    // Obtener todas las canciones
    suspend fun obtenerTodasLasCanciones(): List<Cancion> {
        return try {
            coleccionCanciones.get().await().toObjects(Cancion::class.java)
        } catch (e: Exception) {
            emptyList() // Retorna una lista vacía si ocurre un error
        }
    }

    // Insertar una canción
    suspend fun insertarCancion(cancion: Cancion) {
        try {
            coleccionCanciones.document(cancion.id).set(cancion).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Eliminar una canción por ID
    suspend fun eliminarCancion(id: String) {
        try {
            coleccionCanciones.document(id).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
