@file:Suppress("DEPRECATION") //Elimina las advertencias

package com.example.soundstation.Ventanas.Perfil

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.R
import com.example.soundstation.Ventanas.Home.BottomNavigationBar
import com.example.soundstation.ui.theme.SoundStationTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentanaPerfil(navController: NavHostController) {
    val seleccionMenu = remember { mutableStateOf(2) }
    val auth = FirebaseAuth.getInstance()
    val user: FirebaseUser? = auth.currentUser

    var nombreUsuario by remember { mutableStateOf(user?.displayName ?: "Usuario") }
    var imagenPerfil by remember { mutableStateOf(user?.photoUrl?.toString() ?: "") }

    // Variable para habilitar la edición del nombre
    var isEditingName by remember { mutableStateOf(false) }

    // Launcher para elegir una nueva imagen de perfil
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Subir la nueva imagen a Firebase Storage
            uploadImageToFirebase(it, user) { downloadUrl ->
                imagenPerfil = downloadUrl.toString()
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) // Usa la barra de navegación
        }
    ) { padding ->
        // NavigationBar para la navegación inferior
        NavigationBar(containerColor = Color(0xFFE53935)) {
            // Home Tab
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = seleccionMenu.value == 0,
                onClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                    seleccionMenu.value = 0
                }
            )

            // Buscar Tab
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                label = { Text("Buscar") },
                selected = seleccionMenu.value == 1,
                onClick = {
                    navController.navigate("search") {
                        popUpTo("search") { inclusive = true }
                    }
                    seleccionMenu.value = 1
                }
            )

            // Perfil Tab
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
                label = { Text("Perfil") },
                selected = seleccionMenu.value == 2,
                onClick = {
                    seleccionMenu.value = 2 // Mantiene la pantalla de perfil activa
                }
            )
        }

        // Pantalla del Perfil
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
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                // Imagen de perfil (si no se ha seleccionado, muestra la foto de perfil actual o un ícono por defecto)
                Image(
                    painter = if (imagenPerfil.isNotEmpty()) {
                        painterResource(id = R.drawable.ic_launcher_foreground) // Cambia la imagen al nuevo URL
                    } else {
                        painterResource(id = R.drawable.ic_launcher_foreground) // Imagen por defecto
                    },
                    contentDescription = "Foto de Perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.White, shape = CircleShape)
                        .padding(4.dp)
                        .clickable {
                            launcher.launch("image/*")
                        },
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Editar Nombre de Usuario
                if (isEditingName) {
                    BasicTextField(
                        value = nombreUsuario,
                        onValueChange = { nombreUsuario = it },
                        textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                        modifier = Modifier
                            .background(Color.Gray, shape = CircleShape)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                } else {
                    Text(
                        text = nombreUsuario,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.clickable { isEditingName = true }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Correo del usuario
                Text(
                    text = user?.email ?: "usuario@ejemplo.com",
                    fontSize = 16.sp,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón para cerrar sesión
                Button(
                    onClick = {
                        auth.signOut()
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar Sesión", color = Color.White)
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Enlace clicable para editar perfil
                ClickableText(
                    text = AnnotatedString("Editar Perfil"),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    onClick = {
                        // Lógica para editar perfil
                        navController.navigate("editProfile") // Si tienes una pantalla para editar el perfil
                    }
                )
            }
        }
    }
}

// Función para subir la nueva foto de perfil a Firebase Storage
fun uploadImageToFirebase(uri: Uri, user: FirebaseUser?, onComplete: (Uri) -> Unit) {
    val storageRef: StorageReference = FirebaseStorage.getInstance().reference
    val imageRef = storageRef.child("profileImages/${user?.uid}.jpg")

    val uploadTask = imageRef.putFile(uri)
    uploadTask.addOnSuccessListener {
        imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(downloadUrl)
                .build()
            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onComplete(downloadUrl) // Devuelve el nuevo URL de la foto
                    } else {
                    }
                }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVentanaPerfil() {
    SoundStationTheme {
        VentanaPerfil(navController = rememberNavController())
    }
}
