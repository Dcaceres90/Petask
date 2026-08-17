package com.deviar.petask.common.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.deviar.petask.common.ui.components.button.SpinnerCustom
import com.deviar.petask.common.utils.DificultLevel

@Composable
fun FormAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: (nombre: String, correo: String) -> Unit
) {
    // Variables de estado locales para guardar lo que escribe el usuario
    var nameInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() }, // Se ejecuta al tocar fuera o presionar atrás
        title = {
            Text(text = "Registro de Usuario")
        },
        text = {
            DialogView(
                nameInput = nameInput,
                onValueChangedText = {
                    nameInput = it
                },
                emailInput = emailInput,
                onValueChangedDate = {
                    emailInput = it
                },
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    // Enviamos los datos capturados a la función superior
                    onConfirm(nameInput, emailInput)
                },
                // Opcional: Deshabilitar el botón si algún campo está vacío
                enabled = nameInput.isNotBlank() && emailInput.isNotBlank()
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

@Composable
fun DialogView(
    nameInput: String = "",
    onValueChangedText: (String) -> Unit,
    emailInput: String = "",
    onValueChangedDate: (String) -> Unit,
) {
    // Contenedor vertical para organizar los campos de texto
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Espaciado entre campos
    ) {
        Text(text = "Por favor, introduce tus datos:")

        // Primer campo de entrada
        OutlinedTextField(
            value = nameInput,
            onValueChange = onValueChangedText,
            label = { Text("Nombre completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Row() {
            //Spinner with onclick and alert dialog
            val listado = DificultLevel.entries.map { it.name }
            SpinnerCustom(
                listado = listado
            )
            DatePickerDialogCustom(
                onDateSelected = {
                    // Handle the selected date
                },
                onDismiss = {
                    // Handle the dismiss event
                }
            )
        }

        // Segundo campo de entrada
        OutlinedTextField(
            value = emailInput,
            onValueChange = onValueChangedDate,
            label = { Text("Correo electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
