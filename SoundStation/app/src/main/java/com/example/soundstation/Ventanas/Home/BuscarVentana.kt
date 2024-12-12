package com.example.soundstation

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.Ventanas.Home.BottomNavigationBar
import com.example.soundstation.ui.theme.SoundStationTheme
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarScreen(navController: NavHostController) {
    val busqueda = remember { mutableStateOf("") }
    val canciones = remember { mutableStateListOf<Map<String, String>>() } // Lista de resultados
    var cancionReproduciendo by remember { mutableStateOf<Map<String, String>?>(null) } // Canción que se está reproduciendo
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) } // Reproductor de audio

    val db = FirebaseFirestore.getInstance() // Variable para la llamada de firebase firestore

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF121212)) // Fondo oscuro
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Buscar Música",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Campo de búsqueda
                BasicTextField(
                    value = busqueda.value,
                    onValueChange = { busqueda.value = it },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            buscarCanciones(db, busqueda.value, canciones) // Llamada a la función de búsqueda
                        }
                    ),
                    textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.2f), shape = MaterialTheme.shapes.medium)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Lista de canciones
                Column {
                    canciones.forEach { cancion ->
                        CancionCard(
                            cancion = cancion,
                            isReproduciendo = cancionReproduciendo == cancion,
                            onReproducir = {
                                mediaPlayer?.release() // Libera el reproductor actual
                                mediaPlayer = MediaPlayer().apply {
                                    setDataSource(cancion["url"] ?: "")
                                    prepare()
                                    start()
                                }
                                cancionReproduciendo = cancion
                            },
                            onPausar = {
                                mediaPlayer?.pause()
                                cancionReproduciendo = null
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CancionCard(cancion: Map<String, String>, isReproduciendo: Boolean,onReproducir: () -> Unit,onPausar: () -> Unit) {
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(8.dp))
            .clickable { onReproducir() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(16.dp))

        // Detalles de la canción
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = cancion["titulo"] ?: "Sin título",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = cancion["artista"] ?: "Desconocido",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        // Botón de Reproducir/Pausar
        IconButton(onClick = {
            if (isPlaying) {
                mediaPlayer?.pause()
                isPlaying = false
            } else {
                mediaPlayer?.start()
                isPlaying = true
            }
        }) {
            Icon(
                painter = painterResource(if (isPlaying) R.drawable.icono_pause_24 else R.drawable.icono_play_24),
                contentDescription = "Reproducir/Pausar",
                tint = Color.White
            )
        }
    }
}


/**
 * Función para buscar canciones en Firestore
 */

fun buscarCanciones(db: FirebaseFirestore,termino: String, canciones: MutableList<Map<String, String>>) {
    db.collection("canciones")
        .whereGreaterThanOrEqualTo("titulo", termino)
        .whereLessThanOrEqualTo("titulo", termino + "\uf8ff")
        .get()
        .addOnSuccessListener { querySnapshot ->
            canciones.clear()
            for (document in querySnapshot) {
                val titulo = document.getString("titulo") ?: "Sin título"
                val artista = document.getString("artista") ?: "Desconocido"
                val url = document.getString("url") ?: ""
                canciones.add(mapOf("titulo" to titulo, "artista" to artista, "url" to url))
            }
        }
        .addOnFailureListener { exception -> println("Error buscando canciones: ${exception.message}")
        }
}


@Composable
@Preview(showBackground = true)
fun PreviewBuscarScreen() {
    SoundStationTheme {BuscarScreen(navController = rememberNavController())}
}
