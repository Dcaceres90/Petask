package com.deviar.petask.common.ui.components.dialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
    idUser: String,
    newTaskFormState: NewTaskFormState,
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onValueChangedText: (String) -> Unit,
    onClickConfirm: (TaskModel) -> Unit,
    onClickConfirmDateSpicker: () -> Unit,
    onClickShowDialogDateSpicker: () -> Unit,
    onDismissDateSpicker: () -> Unit,
    onClickSpinner: (String) -> Unit,
) {

    // Variables de estado locales para guardar lo que escribe el usuario

    AlertDialog(
        onDismissRequest = { onDismiss() }, // Se ejecuta al tocar fuera o presionar atrás
        title = {
            Text(text = "Crear Tarea")
        },
        text = {
            DialogView(
                newTaskFormState = newTaskFormState,
                selectedDateLong = selectedDateLong,
                datePickerState = datePickerState,
                onValueChangedTitle = onValueChangedText,
                onClickConfirmDateSpicker = onClickConfirmDateSpicker,
                onClickShowDialogDateSpicker = onClickShowDialogDateSpicker,
                onDismissDateSpicker = onDismissDateSpicker,
                onClickSpinner = onClickSpinner,
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    val newTask = TaskModel(
                        levelDificult = newTaskFormState.levelDificult,
                        text = newTaskFormState.textNewTask,
                        toDoDate = newTaskFormState.dateToDo,
                        idUser = idUser,
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
    datePickerState: DatePickerState,
    selectedDateLong: Long = 0L,
    onValueChangedTitle: (String) -> Unit,
    onClickConfirmDateSpicker: () -> Unit,
    onClickShowDialogDateSpicker: () -> Unit,
    onDismissDateSpicker: () -> Unit,
    onClickSpinner: (String) -> Unit,
) {

    // Contenedor vertical para organizar los campos de texto
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Espaciado entre campos
    ) {
        Text(text = "Por favor, introduce tus datos:")

        // Primer campo de entrada
        OutlinedTextField(
            value = newTaskFormState.title,
            onValueChange = onValueChangedTitle,
            label = { Text("Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        //Spinner with onclick and alert dialog
        val listado = LevelDificult.entries.map { it.name }
        SpinnerCustom(
            listado = listado,
            onClickSpinner = onClickSpinner,
        )

        DatePickerDialogCustom(
            selectedDate = selectedDateLong,
            showDialog = newTaskFormState.showDialog,
            datePickerState = datePickerState,
            onClickConfirm = onClickConfirmDateSpicker,
            onClickShowDialog = onClickShowDialogDateSpicker,
            onDismiss = onDismissDateSpicker,
        )
    }
}
