package com.example.soundstation.Ventanas.Otras

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.soundstation.BuscarScreen
import com.example.soundstation.Ventanas.Home.Canciones.AgregarCancionScreen
import com.example.soundstation.Ventanas.Home.Canciones.VentanaCanciones
import com.example.soundstation.Ventanas.Home.Playlists.CrearPlaylistScreen
import com.example.soundstation.Ventanas.Home.Playlists.DetallePlaylistScreen
import com.example.soundstation.Ventanas.Home.PrincipalVentana
import com.example.soundstation.Ventanas.Login.VentanaCrearCuenta
import com.example.soundstation.Ventanas.Login.VentanaLogin
import com.example.soundstation.Ventanas.Perfil.VentanaPerfil
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(navController: NavHostController) {
    val usuario = FirebaseAuth.getInstance().currentUser

    NavHost(navController = navController,
        startDestination = if (usuario != null) "home" else "login")
    {
        composable("login") { VentanaLogin(navController = navController) } // Pantalla de inicio de sesión
        composable("crear_cuenta") { VentanaCrearCuenta(navController) } //Pantalla de Crecion de Cuenta
        composable("cerrar_sesion") { VentanaLogin(navController) } //Pantalla de iniciar sesion cuando cierras sesión
        composable("home") { PrincipalVentana(navController) } // Pantalla principal
        composable("buscar") { BuscarScreen(navController) } // Pantalla de búsqueda
        composable("perfil") { VentanaPerfil(navController) } // Pantalla de perfil
        composable("crear_playlist"){CrearPlaylistScreen(navController = navController)} //Pantalla de crear playlist
        composable("canciones") { VentanaCanciones(navController) } //Pantalla para ver las canciones
        composable("playlist") { CrearPlaylistScreen(navController = navController) } //Pantalla para ver las playList

        // Ver Playlist
        composable(
            route = "ver_playlist/{playlistId}",
            arguments = listOf(navArgument("playlistId") { type = NavType.StringType }))
        { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
            DetallePlaylistScreen(playlistId = playlistId,navController = navController)
        }

        composable("agregarCancion/{playlistId}") { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
            AgregarCancionScreen(playlistId = playlistId,navController = navController)
        }

    }
}
