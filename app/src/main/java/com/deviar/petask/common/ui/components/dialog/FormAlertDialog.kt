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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.ui.components.button.SpinnerCustom
import com.deviar.petask.common.utils.LevelDificult
import com.deviar.petask.tasks.domain.FormTaskState

@Composable
fun FormAlertDialog(
    selectedDateLong: Long,
    idUser: String,
    formTaskState: FormTaskState,
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onValueChangedText: (String) -> Unit,
    onClickConfirm: (TaskModel) -> Unit,
    onClickConfirmDateSpicker: (Long) -> Unit,
    onClickShowDialogDateSpicker: () -> Unit,
    onDismissDateSpicker: () -> Unit,
    onClickSpinner: (String) -> Unit,
) {

    // Variables de estado locales para guardar lo que escribe el usuario

    AlertDialog(
        onDismissRequest = { onDismiss() }, // Se ejecuta al tocar fuera o presionar atrás
        title = {
            Text(text = "Create Task")
        },
        text = {
            DialogView(
                formTaskState = formTaskState,
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
                        idTask = formTaskState.taskEdit.idTask,
                        levelDificult = formTaskState.taskEdit.levelDificult,
                        text = formTaskState.title,
                        toDoDate = formTaskState.taskEdit.dateToDo,
                        idUser = idUser,
                    )
                    onClickConfirm(newTask)
                },
                // Opcional: Deshabilitar el botón si algún campo está vacío
                enabled =
                    formTaskState.title.isNotBlank() &&
                        formTaskState.taskEdit.dateToDoString.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun DialogView(
    formTaskState: FormTaskState,
    datePickerState: DatePickerState,
    selectedDateLong: Long = 0L,
    onValueChangedTitle: (String) -> Unit,
    onClickConfirmDateSpicker: (Long) -> Unit,
    onClickShowDialogDateSpicker: () -> Unit,
    onDismissDateSpicker: () -> Unit,
    onClickSpinner: (String) -> Unit,
) {

    // Contenedor vertical para organizar los campos de texto
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Espaciado entre campos
    ) {
        Text(text = "Please, fill the fields")

        // Primer campo de entrada
        OutlinedTextField(
            value = formTaskState.title,
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
            showDialog = formTaskState.showDialog,
            datePickerState = datePickerState,
            onClickConfirm = onClickConfirmDateSpicker,
            onClickShowDialog = onClickShowDialogDateSpicker,
            onDismiss = onDismissDateSpicker,
        )
    }
}
