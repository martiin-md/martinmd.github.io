package com.example.soundstation.Ventanas.Home.Canciones

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.soundstation.Ventanas.Home.Playlists.CancionItem
import com.example.soundstation.ViewModel.PlaylistViewModel
import com.example.soundstation.modelo.Cancion
import com.example.soundstation.ui.theme.SoundStationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarCancionScreen(
    playlistId: String,
    navController: NavHostController,
    viewModel: PlaylistViewModel = viewModel()
) {
    // Estado para almacenar las canciones globales (todas las canciones disponibles)
    val cancionesGlobales = remember { mutableStateListOf<Cancion>() }

    // Efecto lanzado al cargar la pantalla para obtener las canciones globales
    LaunchedEffect(Unit) {
        viewModel.cargarCancionesDePlaylist(playlistId) // Asegurarse de que la playlist está cargada
        viewModel.cargarCancionesGlobales() // Método para cargar canciones globales
        cancionesGlobales.clear()
        cancionesGlobales.addAll(viewModel.canciones.value)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) // Usa la barra de navegación corregid
        },
        // Barra superior con el título
        topBar = {
            TopAppBar(
                title = { Text("Agregar Canción") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE53935))
            )
        }
    ) { padding ->
        // Lista de canciones globales
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (cancionesGlobales.isEmpty()) {
                // Mostrar un mensaje si no hay canciones disponibles
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No hay canciones disponibles para agregar.")
                }
            } else {
                LazyColumn {
                    items(cancionesGlobales) { cancion ->
                        CancionItem(
                            cancion = cancion,
                            onDelete = {
                                viewModel.agregarCancion(playlistId, cancion)
                                navController.popBackStack() // Regresar a la pantalla anterior
                            }
                        )
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
                    Icon(Icons.Default.Add, contentDescription = "Agregar Canción")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVentanaAgregarCancion() {
    // Crear valores simulados para el preview
    val fakePlaylistId = "fakePlaylistId" // ID ficticio de la playlist
    val fakeNavController = rememberNavController() // Controlador de navegación simulado
    val fakeViewModel = PlaylistViewModel() // Instancia simulada del ViewModel (asegúrate de que sea seguro para vistas previas)

    SoundStationTheme {
        AgregarCancionScreen(
            playlistId = fakePlaylistId,
            navController = fakeNavController,
            viewModel = fakeViewModel
        )
    }
}

