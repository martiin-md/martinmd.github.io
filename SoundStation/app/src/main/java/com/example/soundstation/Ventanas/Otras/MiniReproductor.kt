package com.example.soundstation.Ventanas.Otras

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@Composable
fun MiniReproductor(context: Context, titulo: String, artista: String, url: String, onExpandReproductor: () -> Unit) {
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(url) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFB61C1C))
            .padding(8.dp)
            .clickable { onExpandReproductor() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen de la canción
        Image(
            painter = painterResource(R.drawable.logosoundstation), // Reemplaza con tu recurso
            contentDescription = "Carátula de la canción",
            modifier = Modifier
                .size(50.dp)
                .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Información de la canción
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = titulo,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1
            )
            Text(
                text = artista,
                color = Color.LightGray,
                fontSize = 14.sp,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

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
