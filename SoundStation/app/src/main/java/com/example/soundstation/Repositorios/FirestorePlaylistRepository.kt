package com.example.soundstation.Repositorios


import com.example.soundstation.modelo.Cancion
import com.example.soundstation.modelo.Playlist
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestorePlaylistRepository {

    private val db = FirebaseFirestore.getInstance()
    private val playlistsCollection = db.collection("playlists")

    // Crear una nueva playlist
    suspend fun crearPlaylist(playlist: Playlist) {
        try {
            playlistsCollection.document(playlist.id).set(playlist).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Obtener todas las playlists
    suspend fun obtenerPlaylists(): List<Playlist> {
        return try {
            playlistsCollection.get().await().toObjects(Playlist::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Agregar una canción a una playlist
    suspend fun agregarCancionAPlaylist(playlistId: String, cancion: Cancion) {
        try {
            playlistsCollection.document(playlistId).collection("canciones")
                .document(cancion.id).set(cancion).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Obtener canciones de una playlist
    suspend fun obtenerCancionesDePlaylist(playlistId: String): List<Cancion> {
        return try {
            playlistsCollection.document(playlistId).collection("canciones")
                .get().await().toObjects(Cancion::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Eliminar una playlist
    suspend fun eliminarPlaylist(playlistId: String) {
        try {
            playlistsCollection.document(playlistId).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Eliminar una canción de una playlist
    suspend fun eliminarCancionDePlaylist(playlistId: String, cancionId: String) {
        try {
            playlistsCollection.document(playlistId).collection("canciones")
                .document(cancionId).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    // **Nuevo método: obtener todas las canciones globales**
    suspend fun obtenerTodasLasCanciones(): List<Cancion> {
        return try {
            db.collection("canciones")
                .get()
                .await()
                .toObjects(Cancion::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun agregarCancionAPlaylist(playlistId: String, cancionId: String) {
        val playlistRef = db.collection("playlists").document(playlistId)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(playlistRef)
            val canciones = snapshot.get("canciones") as? MutableList<String> ?: mutableListOf()
            canciones.add(cancionId)
            transaction.update(playlistRef, "canciones", canciones)
        }.await()
    }

}
