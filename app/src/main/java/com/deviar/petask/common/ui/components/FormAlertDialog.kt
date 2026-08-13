package com.deviar.petask.common.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: (nombre: String, correo: String) -> Unit
) {
    // Variables de estado locales para guardar lo que escribe el usuario
    var nombreInput by remember { mutableStateOf("") }
    var correoInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() }, // Se ejecuta al tocar fuera o presionar atrás
        title = {
            Text(text = "Registro de Usuario")
        },
        text = {
            // Contenedor vertical para organizar los campos de texto
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp) // Espaciado entre campos
            ) {
                Text(text = "Por favor, introduce tus datos:")

                // Primer campo de entrada
                OutlinedTextField(
                    value = nombreInput,
                    onValueChange = { nombreInput = it },
                    label = { Text("Nombre completo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Segundo campo de entrada
                OutlinedTextField(
                    value = correoInput,
                    onValueChange = { correoInput = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Enviamos los datos capturados a la función superior
                    onConfirm(nombreInput, correoInput)
                },
                // Opcional: Deshabilitar el botón si algún campo está vacío
                enabled = nombreInput.isNotBlank() && correoInput.isNotBlank()
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}