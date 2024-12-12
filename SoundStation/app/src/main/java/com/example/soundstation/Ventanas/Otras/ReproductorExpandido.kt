package com.example.soundstation.Ventanas.Otras

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundstation.R


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReproductorExpandido(context: Context, titulo: String, artista: String, url: String, onClose: () -> Unit ) {
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(url) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
        }
    }

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Centra el contenido verticalmente
            ) {
                // Imagen de la canción
                Image(
                    painter = painterResource(R.drawable.logosoundstation),
                    contentDescription = "Carátula de la canción",
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Información de la canción
                Text(
                    text = titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    maxLines = 1
                )
                Text(
                    text = artista,
                    color = Color.Gray,
                    fontSize = 20.sp,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Controles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = { /* Canción anterior */ }) {
                        Icon(
                            painter = painterResource(R.drawable.icono_skip_previous_24),
                            contentDescription = "Anterior"
                        )
                    }

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
                            contentDescription = "Reproducir/Pausar"
                        )
                    }

                    IconButton(onClick = { /* Canción siguiente */ }) {
                        Icon(
                            painter = painterResource(R.drawable.icono_skip_next_24),
                            contentDescription = "Siguiente"
                        )
                    }
                }
            }
        }
    )
}