package com.deviar.petask.common.ui.components.dialog
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
import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.ui.components.button.SpinnerCustom
import com.deviar.petask.common.utils.LevelDificult
import com.deviar.petask.tasks.domain.FormTaskState

import java.util.Date

@Composable
fun FormAlertDialog(
    idUser: String,
    formTaskState: FormTaskState,
    onDismiss: () -> Unit,
    onClickConfirm: (TaskModel) -> Unit,
    onClickConfirmDateSpicker: (Long) -> Unit,
    onClickShowDialogDateSpicker: () -> Unit,
    onValueChange: (String) -> Unit,
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
                onClickConfirmDateSpicker = onClickConfirmDateSpicker,
                onClickShowDialogDateSpicker = onClickShowDialogDateSpicker,
                onDismissDateSpicker = onDismissDateSpicker,
                onValueChange = onValueChange,
                onClickSpinner = onClickSpinner,
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    val newTask = TaskModel(
                        idTask = formTaskState.taskEdit?.idTask ?: 0,
                        levelDificult = formTaskState.taskEdit?.levelDificult ?: LevelDificult.EASY,
                        text = formTaskState.title,
                        toDoDate = formTaskState.taskEdit?.dateToDo ?: Date(),
                        idUser = idUser,
                    )
                    onClickConfirm(newTask)
                },
                // Opcional: Deshabilitar el botón si algún campo está vacío
                enabled =
                    formTaskState.title.isNotBlank() &&
                        formTaskState.taskEdit?.levelDificult?.name?.isNotBlank() == true &&
                        formTaskState.taskEdit?.dateToDoString?.isNotBlank() == true
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
    onClickConfirmDateSpicker: (Long) -> Unit,
    onClickShowDialogDateSpicker: () -> Unit,
    onDismissDateSpicker: () -> Unit,
    onValueChange: (String) -> Unit,
    onClickSpinner: (String) -> Unit,
) {
    var titleSelected by remember {
        mutableStateOf(formTaskState.title)
    }
    var levelDificultSelected by remember {
        mutableStateOf(formTaskState.taskEdit?.levelDificult?.name?: LevelDificult.EASY.name)
    }

    // Contenedor vertical para organizar los campos de texto
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp), // Espaciado entre campos
    ) {
        Text(text = "Please, fill the fields")

        // Primer campo de entrada
        OutlinedTextField(
            value = titleSelected,
            onValueChange = {
                titleSelected = it
                onValueChange(it)
            },
            label = { Text("Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        //Spinner with onclick and alert dialog
        val list = LevelDificult.entries.map { it.name }
        SpinnerCustom(
            list = list,
            selected = levelDificultSelected,
            onClickSpinner = onClickSpinner,
        )

        DatePickerDialogCustom(
            selectedDate = formTaskState.taskEdit?.dateToDo?.time ?: Date().time,
            showDialog = formTaskState.showDialog,
            onClickConfirm = onClickConfirmDateSpicker,
            onClickShowDialog = onClickShowDialogDateSpicker,
            onDismiss = {
                onDismissDateSpicker()
            },
        )
    }
}
