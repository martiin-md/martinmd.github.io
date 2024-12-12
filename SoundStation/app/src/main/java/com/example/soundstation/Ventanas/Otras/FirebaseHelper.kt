package com.example.soundstation.Ventanas.Otras

import com.google.firebase.storage.FirebaseStorage

object FirebaseHelper {
    private val storage = FirebaseStorage.getInstance()
    private val songsRef = storage.reference.child("soundstation")

    fun getSongList(onComplete: (List<String>) -> Unit) {
        songsRef.listAll()
            .addOnSuccessListener { result ->
                val songNames = result.items.map { it.name } // Nombres de las canciones
                onComplete(songNames)
            }
            .addOnFailureListener {
                onComplete(emptyList()) // En caso de error
            }
    }

    fun getSongUrl(songName: String, onComplete: (String) -> Unit) {
        songsRef.child(songName).downloadUrl
            .addOnSuccessListener { uri ->
                onComplete(uri.toString())
            }
            .addOnFailureListener {
                onComplete("") // En caso de error
            }
    }
}
