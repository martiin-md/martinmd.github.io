package com.example.soundstation.Ventanas.Home
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.R
import com.example.soundstation.ui.theme.SoundStationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalVentana(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController) //Uso la barra de navegación inferior
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE53935)) // Fondo rojo
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logosoundstation),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp)
                )
                // Título de la pantalla principal
                Text(
                    text = "SoundStation",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón para Crear Playlist
                Button(
                    onClick = {
                        // Asegúrate de navegar correctamente
                        navController.navigate("crear_playlist")  // navego a la ventana de crear las playlist
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61)),
                    modifier = Modifier
                         .fillMaxWidth()
                ) {
                    Text("Crear Playlist", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para Ver Canciones
                Button(
                    onClick = {
                        navController.navigate("ver_playlist/{playlistId}") //Navego a la ventana canciones
                        // TODO: Navegar a la pantalla para ver las listas de canciones "Playlist"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver Playlist", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))


                // Botón para Ver Canciones
                Button(
                    onClick = {
                        navController.navigate("canciones") //Navego a la ventana canciones
                        // TODO: Navegar a la pantalla para ver canciones
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver Canciones", color = Color.White)
                }
            }
        }
    }
}



@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentDestination = navController.currentBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color(0xFFB71C1C)) {
        NavigationBarItem(
            selected = currentDestination == "home",
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) },
            label = { Text("Home", color = Color.White) }
        )
        NavigationBarItem(
            selected = currentDestination == "buscar",
            onClick = { navController.navigate("buscar") },
            icon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White) },
            label = { Text("Buscar", color = Color.White) }
        )
        NavigationBarItem(
            selected = currentDestination == "perfil",
            onClick = { navController.navigate("perfil") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White) },
            label = { Text("Perfil", color = Color.White) }
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewMainScreen() {
    SoundStationTheme {
        PrincipalVentana(navController = rememberNavController())
    }
}
