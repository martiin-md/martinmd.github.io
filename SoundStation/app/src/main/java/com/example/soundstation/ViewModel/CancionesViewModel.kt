package com.example.soundstation.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundstation.Repositorios.FirestoreCancionRepository
import com.example.soundstation.modelo.Cancion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CancionesViewModel : ViewModel() {

    private val repository = FirestoreCancionRepository()

    private val _canciones = MutableStateFlow<List<Cancion>>(emptyList())
    val canciones: StateFlow<List<Cancion>> get() = _canciones

    // Cargar todas las canciones
    fun cargarCanciones() {
        viewModelScope.launch {
            _canciones.value = repository.obtenerTodasLasCanciones()
        }
    }

    // Insertar una canción
    fun agregarCancion(cancion: Cancion) {
        viewModelScope.launch {
            repository.insertarCancion(cancion)
            cargarCanciones() // Actualiza la lista
        }
    }

    // Eliminar una canción
    fun eliminarCancion(id: String) {
        viewModelScope.launch {
            repository.eliminarCancion(id)
            cargarCanciones() // Actualiza la lista
        }
    }
}
