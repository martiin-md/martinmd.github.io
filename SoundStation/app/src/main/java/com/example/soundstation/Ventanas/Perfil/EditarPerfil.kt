@file:Suppress("DEPRECATION") //Elimina las advertencias
package com.example.soundstation.Ventanas.Perfil
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundstation.ui.theme.SoundStationTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfilScreen() {
    val seleccionMenu = remember { mutableStateOf(2) } // Perfil seleccionado por defecto
    val nombreUsuario = remember { mutableStateOf("") }
    val correoUsuario = remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFE53935)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = seleccionMenu.value == 0,
                    onClick = { seleccionMenu.value = 0 } // TODO: Navegar a Home
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                    label = { Text("Buscar") },
                    selected = seleccionMenu.value == 1,
                    onClick = { seleccionMenu.value = 1 } // TODO: Navegar a Buscar
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = seleccionMenu.value == 2,
                    onClick = { seleccionMenu.value = 2 } // Mantiene en Perfil
                )
            }
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
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Editar Perfil",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Campo para el nombre de usuario
                OutlinedTextField(
                    value = nombreUsuario.value,
                    onValueChange = { nombreUsuario.value = it },
                    label = { Text("Nombre de Usuario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.LightGray,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo para el correo electrónico
                OutlinedTextField(
                    value = correoUsuario.value,
                    onValueChange = { correoUsuario.value = it },
                    label = { Text("Correo Electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.LightGray,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón para guardar los cambios
                Button(
                    onClick = {
                        // TODO: Implementar lógica para guardar cambios
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Cambios", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para cancelar
                OutlinedButton(
                    onClick = {
                        // TODO: Implementar lógica para cancelar cambios
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar", color = Color.White)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEditarPerfilScreen() {
    SoundStationTheme {
        EditarPerfilScreen()
    }
}
