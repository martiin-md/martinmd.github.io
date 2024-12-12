package com.example.soundstation.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundstation.Repositorios.FirestorePlaylistRepository
import com.example.soundstation.modelo.Cancion
import com.example.soundstation.modelo.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel : ViewModel() {

    private val repository = FirestorePlaylistRepository()

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> get() = _playlists

    private val _canciones = MutableStateFlow<List<Cancion>>(emptyList())
    val canciones: StateFlow<List<Cancion>> get() = _canciones

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error



    // Cargar todas las playlists
    fun cargarPlaylists() {
        viewModelScope.launch {
            _playlists.value = repository.obtenerPlaylists()
        }
    }

    // Crear una nueva playlist
    fun crearPlaylist(playlist: Playlist) {
        viewModelScope.launch {
            repository.crearPlaylist(playlist)
            cargarPlaylists() // Actualizar la lista
        }
    }

    // Cargar canciones de una playlist específica
    fun cargarCancionesDePlaylist(playlistId: String) {
        viewModelScope.launch {
            _canciones.value = repository.obtenerCancionesDePlaylist(playlistId)
        }
    }

    // Agregar una canción a una playlist
    fun agregarCancion(playlistId: String, cancion: Cancion) {
        viewModelScope.launch {
            repository.agregarCancionAPlaylist(playlistId, cancion)
            cargarCancionesDePlaylist(playlistId) // Actualizar la lista de canciones
        }
    }

    // Eliminar una canción de una playlist
    fun eliminarCancion(playlistId: String, cancionId: String) {
        viewModelScope.launch {
            repository.eliminarCancionDePlaylist(playlistId, cancionId)
            cargarCancionesDePlaylist(playlistId)
        }
    }
    //Metodo de cargar todas las canciones
    fun cargarCancionesGlobales(){
        viewModelScope.launch {
            _canciones.value = repository.obtenerTodasLasCanciones()

        }
    }

    // Agregar una canción a una playlist
    fun agregarCancionAPlaylist(playlistId: String, cancionId: String) {
        viewModelScope.launch {
            repository.agregarCancionAPlaylist(playlistId, cancionId)
            cargarCancionesDePlaylist(playlistId) // Actualizar la lista de canciones
        }
    }

    // Agregar una canción a una playlist usando el ID de la canción
    fun agregarCancionAPlaylist1(playlistId: String, cancionId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.agregarCancionAPlaylist(playlistId, cancionId)
                cargarCancionesDePlaylist(playlistId) // Actualizar la lista de canciones
            } catch (e: Exception) {
                _error.value = "Error al agregar la canción: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Eliminar una playlist
    fun eliminarPlaylist(playlistId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.eliminarPlaylist(playlistId)
                cargarPlaylists() // Actualizar la lista de playlists
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al eliminar la playlist: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
