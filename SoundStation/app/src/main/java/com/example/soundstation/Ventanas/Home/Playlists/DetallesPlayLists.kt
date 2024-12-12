package com.example.soundstation.Ventanas.Home.Playlists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.Ventanas.Home.BottomNavigationBar
import com.example.soundstation.ViewModel.PlaylistViewModel
import com.example.soundstation.modelo.Cancion
import com.example.soundstation.modelo.Playlist
import com.example.soundstation.ui.theme.SoundStationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePlaylistScreen(playlistId: String,navController: NavHostController,playlistViewModel: PlaylistViewModel = viewModel()) {
    // Estados para almacenar la playlist y sus canciones asociadas
    val playlist = remember { mutableStateOf<Playlist?>(null) }
    val canciones = remember { mutableStateListOf<Cancion>() }

    // Efecto lanzado para cargar la playlist y las canciones asociadas al iniciar
    LaunchedEffect(playlistId) {
        playlistViewModel.cargarPlaylists() // Cargar todas las playlists
        playlist.value = playlistViewModel.playlists.value.find { it.id == playlistId }

        playlist.value?.let { p ->
            playlistViewModel.cargarCancionesDePlaylist(p.id)
            canciones.clear()
            canciones.addAll(playlistViewModel.canciones.value)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) // Usa la barra de navegación corregid
        },
        // Barra superior con el nombre de la playlist
        topBar = {
            TopAppBar(
                title = { Text(playlist.value?.nombre ?: "Detalle de Playlist") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE53935))
            )
        },
        // Botón flotante para agregar canciones
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("agregarCancion/$playlistId") // Navegar a la pantalla de agregar canción
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Canción")
            }
        }
    ) { padding ->
        // Contenedor principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (canciones.isEmpty()) {
                // Mostrar un mensaje si no hay canciones en la playlist
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No hay canciones en esta playlist.")
                }
            } else {
                // Lista de canciones en la playlist
                LazyColumn {
                    items(canciones) { cancion ->
                        CancionItem(
                            cancion = cancion,
                            onDelete = {
                                playlist.value?.let { p ->
                                    playlistViewModel.eliminarCancion(p.id, cancion.id)
                                    canciones.remove(cancion) // Actualizar la lista localmente
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CancionItem(cancion: Cancion, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = cancion.titulo, fontWeight = FontWeight.Bold)
                Text(text = cancion.artista)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar Canción")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDetallesPlaylist() {
    SoundStationTheme {
        val fakeNavController = rememberNavController() // Crear un NavController ficticio para el preview
        DetallePlaylistScreen(
            playlistId = "testPlaylistId",
            navController = fakeNavController,
        )
    }
}
