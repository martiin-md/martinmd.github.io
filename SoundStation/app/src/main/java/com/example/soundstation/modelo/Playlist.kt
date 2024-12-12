package com.example.soundstation.modelo


data class Playlist(
    val id: String = "",
    val nombre: String = "",
    val canciones: List<String> = emptyList(), // IDs de canciones
    val creador :  String = "",
    val fechaCreacion : Long
)