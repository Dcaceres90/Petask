package com.deviar.petask.common.ui.components.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deviar.petask.common.ui.components.button.SpinnerCustom
import com.deviar.petask.common.utils.DificultLevel

@Composable
fun FormAlertDialog(
    selectedDate: Long,
    onDismiss: () -> Unit,
    onConfirm: (nombre: String, correo: String) -> Unit,
    onDateSelected: (Long?) -> Unit,
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
                selectedDate = selectedDate,
                nameInput = nameInput,
                onValueChangedText = {
                    nameInput = it
                },
                onDateSelected = onDateSelected,
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
    selectedDate: Long = 0L,
    nameInput: String = "",
    onValueChangedText: (String) -> Unit,
    onValueChangedDate: (String) -> Unit,
    onDateSelected: (Long?) -> Unit ,
) {
    var textDateTask by remember { mutableStateOf("Fecha de la tarea") }
    var showDatePickerDialog by remember { mutableStateOf(false) }

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
            label = { Text("Descripción de la tarea") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        //Spinner with onclick and alert dialog
        val listado = DificultLevel.entries.map { it.name }
        SpinnerCustom(
            listado = listado
        )

        // Segundo campo de entrada
        Text(
            text = textDateTask,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDatePickerDialog = true
                    },
        )
        if (showDatePickerDialog) {
            DatePickerDialogCustom(
                selectedDate = selectedDate,
                onDateSelected = onDateSelected,
                onDismiss = {
                    // Handle the dismiss event
                    showDatePickerDialog = false
                }
            )
        }
    }
}
