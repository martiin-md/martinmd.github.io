package com.example.soundstation.Ventanas.Home.Canciones

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.soundstation.Ventanas.Home.BottomNavigationBar
import com.example.soundstation.Ventanas.Otras.MiniReproductor
import com.example.soundstation.Ventanas.Otras.ReproductorExpandido
import com.example.soundstation.modelo.Cancion
import com.example.soundstation.ui.theme.SoundStationTheme
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentanaCanciones(navController: NavHostController) {
    val canciones = remember { mutableStateListOf<Cancion>() }
    val isLoading = remember { mutableStateOf(true) }
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    var cancionSeleccionada by remember { mutableStateOf<Cancion?>(null) }
    var isExpanded by remember { mutableStateOf(false) } // Estado para controlar mini o reproductor expandido



    LaunchedEffect(Unit) {
        try {
            val snapshot = db.collection("canciones").get().await()
            val cancionesFirestore = snapshot.documents.map { doc ->
                Cancion(
                    id = doc.id,
                    titulo = doc.getString("titulo") ?: "Sin título",
                    artista = doc.getString("artista") ?: "Desconocido",
                    url = doc.getString("url") ?: ""
                )
            }
            canciones.clear()
            canciones.addAll(cancionesFirestore)
        } catch (e: Exception) {
            Log.e("ErrorFirebase", "Error al cargar canciones: ${e.message}")
        } finally {
            isLoading.value = false
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) // Usa la barra de navegación corregid
        },
        topBar = {
            TopAppBar(
                title = { Text("Canciones") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFFB61C1C))
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(canciones) { cancion ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable { cancionSeleccionada = cancion },
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = cancion.titulo,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = cancion.artista,
                                        color = Color.Gray,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Mini Reproductor
                    cancionSeleccionada?.let { cancion ->
                        if (!isExpanded) {
                            MiniReproductor(
                                context = context,
                                titulo = cancion.titulo,
                                artista = cancion.artista,
                                url = cancion.url
                            ) {
                                isExpanded = true
                            }
                        } else {
                            ReproductorExpandido(
                                context = context,
                                titulo = cancion.titulo,
                                artista = cancion.artista,
                                url = cancion.url
                            ) {
                                isExpanded = false
                            }
                        }
                    }
                }
            }
        }
    )
}



fun reproducirCancion(context: Context, url: String) {
    val mediaPlayer = MediaPlayer()
    try {
        mediaPlayer.apply {
            setDataSource(url)
            setOnPreparedListener {
                start() // Inicia la reproducción cuando esté listo
            }
            prepareAsync() // Usar prepareAsync en lugar de prepare para URLs remotas
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error al reproducir la canción: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewVentanaCanciones() {
    SoundStationTheme {
        // Vista previa sin navegación
    }
}
