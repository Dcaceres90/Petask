package com.deviar.petask.common.ui.components.dialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.ui.components.button.SpinnerCustom
import com.deviar.petask.common.utils.LevelDificult
import com.deviar.petask.tasks.domain.NewTaskFormState
import java.util.Date

@Composable
fun FormAlertDialog(
    selectedDateLong: Long,
    newTaskFormState: NewTaskFormState,
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit,
    onValueChangedText: (String) -> Unit,
    onClickConfirm: (TaskModel) -> Unit,
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
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    val newTask = TaskModel(
                        levelDificult = newTaskFormState.levelDificult,
                        text = newTaskFormState.textNewTask,
                        //toDoDate = newTaskFormState.
                    )
                    onClickConfirm(newTask)
                },
                // Opcional: Deshabilitar el botón si algún campo está vacío
                enabled = newTaskFormState.title.isNotBlank() && newTaskFormState.dateToDoString.isNotBlank()
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
    onClickDateSelected: (Long?) -> Unit,
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var enabledDateSpicker by remember { mutableStateOf(false) }

    var selectedDateMillis by remember { mutableLongStateOf(selectedDateLong) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateLong
    )
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

        DatePickerDialogCustom(
            selectedDate = selectedDateLong,
            showDialog = newTaskFormState.showDialog,
            onClickConfirm = {
                selectedDateMillis = datePickerState.selectedDateMillis ?: 0L
                newTaskFormState.dateToDo = Date(datePickerState.selectedDateMillis ?: 0L)
                newTaskFormState.showDialog = false
            },
            onClickShowDialog = {
                newTaskFormState.showDialog = true
            },
            onDismiss = {
                // Handle the dismiss event
                enabledDateSpicker = false
                showDatePickerDialog = false
            },
        )
    }
}
