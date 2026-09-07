package com.deviar.petask.tasks.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deviar.petask.common.ui.components.listas.ListHorizontalCustom
import com.deviar.petask.common.ui.theme.PetaskTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.ui.components.dialog.FormAlertDialog
import com.deviar.petask.common.ui.components.button.ButtonFloating
import com.deviar.petask.common.utils.LevelDificult
import com.deviar.petask.tasks.domain.TasksState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.String

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    viewModel: TaskViewModel,
) {
    var showFormAlertDialog by remember { mutableStateOf(false) }
    val uiTasksState by viewModel.uiTasksState.collectAsStateWithLifecycle()
    val newTaskFormState by viewModel.newTaskFormState.collectAsStateWithLifecycle()

    var selectedDateMillis by remember { mutableLongStateOf(Date().time) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis
    )
    viewModel.updateScreenDatesList(
        datesUpcoming = viewModel.getUpcomingDates(),
    )
    viewModel.getTasksByDate(Date(datePickerState.selectedDateMillis!!))
    viewModel.collectedSucessTask()
    viewModel.getUID()

    Box(
        modifier = Modifier.padding(
            start = 20.dp,
            top = 60.dp,
        )
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                ButtonFloating(
                    modifier = Modifier.padding(
                        end = 20.dp,
                        bottom = 60.dp,
                    ),
                    onClickFloating = {
                        showFormAlertDialog = true
                    }
                )
            },
        ) { paddingValues ->
            // contenido de la pantalla
            Column(modifier = Modifier.padding(paddingValues)) {
                if (showFormAlertDialog) {
                    FormAlertDialog(
                        newTaskFormState = newTaskFormState,
                        idUser = viewModel.uuidState.value,
                        selectedDateLong = selectedDateMillis,
                        datePickerState = datePickerState,
                        onDismiss = {
                                showFormAlertDialog = false
                            },
                        onValueChangedText = {
                            viewModel.updateTitleNewTaskFormScreen(title = it)
                        },
                        onClickConfirm = { newTask ->
                            // Enviamos los datos capturados a la función superior
                            viewModel.insertTaskDataBase(newTask)
                            showFormAlertDialog = false
                        },
                        onClickConfirmDateSpicker = {
                            selectedDateMillis = datePickerState.selectedDateMillis ?: 0L
                            newTaskFormState.showDialog = false
                            //Capaz cambiar falel guardado del date

                            val displayDate = selectedDateMillis.let {
                                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                sdf.format(Date(it))
                            } ?: ""
                            viewModel.updateNewTaskFormScreenDateToDoString(
                                selectedDate = displayDate,
                            )
                            viewModel.updateNewTaskFormScreenDateToDo(
                                selectedDate = Date(selectedDateMillis),
                            )
                            viewModel.setTasksShowDialog(false)
                        },
                        onClickShowDialogDateSpicker = {
                            viewModel.setTasksShowDialog(true)
                        },
                        onDismissDateSpicker = {
                            viewModel.setTasksShowDialog(true)
                        },
                        onClickSpinner = { entry ->
                            LevelDificult.entries.forEach { levelDificult ->
                                if(entry == levelDificult.name) {
                                    viewModel.updateLevelDificultNewTaskFormScreen(levelDificult)
                                }
                            }
                        }
                    )
                }
                TaskStructureScreen(
                    uiTasksState,
                    onClickDate = { date ->
                        viewModel.updateScreenSelectedDate(
                            selectedDateList = date,
                        )
                    },
                )
            }
        }
    }

}

@Composable
fun TaskStructureScreen(
    tasksState: TasksState,
    onClickDate:(String) -> Unit = {}
) {
    Column {
        ListaFechas(
            dates = tasksState.datesUpcoming,
            onClickDate = onClickDate
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListaTask(
            itemList = tasksState.taskList,
        )
    }
}

@Composable
fun ListaFechas(
    dates: List<String>,
    onClickDate:(String) -> Unit = {},
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        // Mostrar los botones para cada fecha
        Row {
            ListHorizontalCustom(
                items = dates,
                onClickItem = onClickDate,
            )
        }
    }
}

@Composable
fun ListaTask(
    itemList: List<TaskModel>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(itemList) { item ->
                FilledIconottomCustom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                        ),
                    onClickArrow = {
                        /* Mostrar DatePickerDialog aquí */
                    },
                    itemTask = item
                )
            }
        }

    }
}

@Composable
fun  FilledIconottomCustom(
    modifier: Modifier = Modifier,
    onClickArrow: () -> Unit,
    itemTask: TaskModel?,
    imageVector: ImageVector = Icons.Filled.Delete,
    colorContent: Color = Color.White,
) {
    FilledIconButton(
        modifier = modifier,
        onClick = onClickArrow,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val time = dateFormat.format(itemTask?.toDoDate?.time)

            Text(
                "${itemTask?.text} ${itemTask?.levelDificult?.name} $time",
                color = colorContent,
            )
            Icon(
                imageVector = imageVector,
                contentDescription = "Regresar",
                tint = colorContent,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    showBackground = true,
)
@Composable
fun PreviewTasksScreen() {
    PetaskTheme {
        TaskStructureScreen(
            TasksState(),
            onClickDate = {},
        )
    }
}