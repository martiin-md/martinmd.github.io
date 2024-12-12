@file:Suppress("DEPRECATION") //Elimina las advertencias
package com.example.soundstation.Ventanas.Home.Playlists

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.Ventanas.Home.BottomNavigationBar
import com.example.soundstation.ViewModel.PlaylistViewModel
import com.example.soundstation.modelo.Playlist
import com.example.soundstation.ui.theme.SoundStationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearPlaylistScreen(navController: NavHostController, viewModel: PlaylistViewModel = viewModel()) {
    val nombrePlaylist = remember { mutableStateOf("") }
    val cancionesSeleccionadas = remember { mutableStateOf<List<String>>(emptyList()) }
    val seleccionMenu = remember { mutableStateOf(0) }
    val cancionesDisponibles by viewModel.canciones.collectAsState(initial = emptyList()) // Estado para manejar la lista de canciones disponibles


    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) // uso la barra de navegación inferior
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE53935))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Crear Playlist",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = nombrePlaylist.value,
                    onValueChange = { nombrePlaylist.value = it },
                    label = { Text("Nombre de la Playlist") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.White,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Selecciona canciones para agregar:")
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(cancionesDisponibles) { cancion ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    // Agregar canción a la lista
                                    cancionesSeleccionadas.value =
                                        cancionesSeleccionadas.value + cancion.id
                                }
                        ) {
                            Text(cancion.titulo, modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val playlist = Playlist(
                            id = System.currentTimeMillis().toString(),
                            nombre = nombrePlaylist.value,
                            creador = "Nombre", // Nombre del usuario
                            fechaCreacion = System.currentTimeMillis(),
                            canciones = cancionesSeleccionadas.value // Pasar las canciones seleccionadas
                        )
                        viewModel.crearPlaylist(playlist)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Playlist", color = Color.White)
                }
            }
        }
    }
}


@Composable
fun PlaylistItem(playlist: Playlist, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = playlist.nombre, fontWeight = FontWeight.Bold)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCrearPlaylistVentana() {
    SoundStationTheme {
        CrearPlaylistScreen(navController = rememberNavController())
    }
}
