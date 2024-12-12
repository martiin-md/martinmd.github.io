package com.example.soundstation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.Ventanas.Otras.AppNavigation
import com.example.soundstation.ui.theme.SoundStationTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoundStationTheme {
                val navController = rememberNavController()

                // Verifica si el usuario está autenticado y redirige a la pantalla correspondiente
                LaunchedEffect(key1 = true) {
                    val usuario = FirebaseAuth.getInstance().currentUser
                    if (usuario != null) {
                        // Si el usuario está autenticado, ir directamente a "home"
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }

                AppNavigation(navController = navController) // Configuración del NavHost principal

            }
        }
    }
}
