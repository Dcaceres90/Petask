package com.deviar.petask.common.ui.components.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.deviar.petask.common.ui.components.button.SpinnerCustom
import com.deviar.petask.common.utils.LevelDificult
import com.deviar.petask.tasks.domain.NewTaskFormState

@Composable
fun FormAlertDialog(
    selectedDateLong: Long,
    newTaskFormState: NewTaskFormState,
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit,
    onValueChangedText: (String) -> Unit,
    onClickConfirm: () -> Unit,
    onValueChangedDate: (String) -> Unit,
) {
    // Variables de estado locales para guardar lo que escribe el usuario

    AlertDialog(
        onDismissRequest = { onDismiss() }, // Se ejecuta al tocar fuera o presionar atrás
        title = {
            Text(text = "Registro de Usuario")
        },
        text = {
            DialogView(
                newTaskFormState = newTaskFormState,
                selectedDateLong = selectedDateLong,
                onValueChangedTitle = onValueChangedText,
                onClickDateSelected = onDateSelected,
                onValueChangedDate = onValueChangedDate,
            )
        },
        confirmButton = {
            Button(
                onClick = onClickConfirm,
                // Opcional: Deshabilitar el botón si algún campo está vacío
                enabled = newTaskFormState.title.isNotBlank() && newTaskFormState.dateToDo.isNotBlank()
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
    newTaskFormState: NewTaskFormState,
    selectedDateLong: Long = 0L,
    onValueChangedTitle: (String) -> Unit,
    onValueChangedDate: (String) -> Unit,
    onClickDateSelected: (Long?) -> Unit,
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var enabledDateSpicker by remember { mutableStateOf(false) }
    val modifer =
            Modifier
                .fillMaxWidth()
                .clickable {
                    enabledDateSpicker = true
                    showDatePickerDialog = true
                }

    // Contenedor vertical para organizar los campos de texto
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Espaciado entre campos
    ) {
        Text(text = "Por favor, introduce tus datos:")

        // Primer campo de entrada
        OutlinedTextField(
            value = "Título",
            onValueChange = onValueChangedTitle,
            label = { Text(newTaskFormState.title) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        //Spinner with onclick and alert dialog
        val listado = LevelDificult.entries.map { it.name }
        SpinnerCustom(
            listado = listado
        )

        Box(
            modifier = modifer
        ) {
            OutlinedTextField(
                value = "Fecha de la tarea",
                onValueChange = onValueChangedDate,
                readOnly = true,
                enabled = enabledDateSpicker,
                label = { Text(newTaskFormState.dateToDo) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (showDatePickerDialog) {
            DatePickerDialogCustom(
                selectedDate = selectedDateLong,
                onDateSelected = onClickDateSelected,
                onDismiss = {
                    // Handle the dismiss event
                    enabledDateSpicker = false
                    showDatePickerDialog = false
                }
            )
        }
    }
}
