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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.ui.components.dialog.FormAlertDialog
import com.deviar.petask.common.ui.components.button.ButtonFloating
import com.deviar.petask.common.utils.LevelDificult
import com.deviar.petask.common.utils.SwipeToDeleteContainer
import com.deviar.petask.tasks.domain.DateState
import com.deviar.petask.tasks.domain.HorizotalListState
import com.deviar.petask.tasks.domain.NewTaskFormState
import com.deviar.petask.tasks.domain.TaskState
import com.deviar.petask.tasks.domain.TasksListUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    viewModel: TaskViewModel,
    uiTasks: TasksListUiState,
) {
    //TODO Mejorar las animaciones
    // Revizar la actualizacion de una lista a otra o pensar que paner cuando no hay tareas en ese dia
    // Cambiar los nombres de los state por mas entendibles
    // Hacer una clase de respuesta
    // Esta actualizando mal las listas
    var showFormAlertDialog by remember { mutableStateOf(false) }
    var isEditTask by remember { mutableStateOf(false) }
    val horizotalListState by viewModel.horizotalListState.collectAsStateWithLifecycle()
    val newTaskFormState by viewModel.newTaskFormState.collectAsStateWithLifecycle()

    viewModel.updateScreenDatesList(
        datesUpcoming = viewModel.getUpcomingDates(),
    )
    var selectedDateMillis by remember {
        mutableLongStateOf(value = horizotalListState.selectedDate.date.time)
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis
    )

    viewModel.getTasksByDate(selectedDate = Date(horizotalListState.selectedDate.date.time))
    viewModel.collectedSucessTask()
    viewModel.getUID()
    Box(
        modifier = Modifier.padding(
            start = 20.dp,
            top = 80.dp,
        )
    ) {
        Column {
            ListaFechas(
                dates = horizotalListState.datesUpcoming,
                onClickDate = { date ->
                    viewModel.updateScreenSelectedDate(
                        selectedDate = date,
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            when (uiTasks) {
                is TasksListUiState.Loading -> {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is TasksListUiState.Error -> {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "Error: ${uiTasks.message}")
                    }
                }

                is TasksListUiState.Success -> {
                    SuccessTasksListScreen(
                        taskList = uiTasks.taskList,
                        viewModel = viewModel,
                        showFormAlertDialog = showFormAlertDialog,
                        newTaskFormState = newTaskFormState,
                        datePickerState = datePickerState,
                        selectedDateMillis = selectedDateMillis,
                        onClickFloating = {
                            showFormAlertDialog = true
                            isEditTask = false
                        },
                        onDismiss = {
                            showFormAlertDialog = false
                        },
                        onClickConfirmDateSpicker = {
                            selectedDateMillis = it
                            newTaskFormState.showDialog = false
                            //Capaz cambiar falel guardado del date

                            val displayDate = selectedDateMillis.let { time ->
                                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                sdf.format(Date(time))
                            } ?: ""
                            viewModel.updateNewTaskFormScreenDateToDoString(
                                selectedDate = displayDate,
                            )
                            viewModel.updateNewTaskFormScreenDateToDo(
                                selectedDate = Date(selectedDateMillis),
                            )
                            viewModel.setTasksShowDialog(false)
                        },
                        onClickConfirm = { newTask ->
                            // Enviamos los datos capturados a la función superior
                            if (!isEditTask) {
                                viewModel.insertTaskDataBase(newTask)
                            } else {
                                viewModel.updateTaskDataBase(newTask)
                            }
                            showFormAlertDialog = false
                        },
                        onClickArrow = {
                            //Hay que pensar como cambiar el FormAlertDialog
                            val displayDate = it.toDoDate.time.let { time ->
                                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                sdf.format(Date(time))
                            } ?: ""
                            val taskState = TaskState(
                                idTask = it.idTask,
                                dateToDo = it.toDoDate,
                                dateToDoString = displayDate,
                                levelDificult = it.levelDificult,
                                textNewTask = it.text,
                            )
                            viewModel.updateTaskFormScreen(taskState)
                            isEditTask = true
                            showFormAlertDialog = true
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun SuccessTasksListScreen(
    taskList: List<TaskModel> = arrayListOf(),
    viewModel: TaskViewModel,
    showFormAlertDialog: Boolean,
    newTaskFormState: NewTaskFormState,
    datePickerState: DatePickerState,
    selectedDateMillis: Long,
    onClickFloating: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onClickConfirmDateSpicker: (Long) -> Unit,
    onClickConfirm: (TaskModel) -> Unit,
    onClickArrow: (TaskModel) -> Unit,
) {
    Box {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                ButtonFloating(
                    modifier = Modifier.padding(
                        end = 20.dp,
                        bottom = 60.dp,
                    ),
                    onClickFloating = onClickFloating,
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
                        onDismiss = onDismiss,
                        onValueChangedText = {
                            viewModel.updateTitleNewTaskFormScreen(title = it)
                        },
                        onClickConfirm = onClickConfirm,
                        onClickConfirmDateSpicker = onClickConfirmDateSpicker,
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
                    taskList = taskList,
                    onDeleted = {
                        viewModel.deleteTaskDataBase(it.idTask)
                    },
                    onClickArrow = onClickArrow,
                )
            }
        }
    }
}

@Composable
fun TaskStructureScreen(
    taskList: List<TaskModel> = arrayListOf(),
    onDeleted: (TaskModel) -> Unit,
    onClickArrow:(TaskModel) -> Unit,
) {
    Column {
        if (taskList.isNotEmpty()) {
            ListaTask(
                itemList = taskList,
                onDeleted = onDeleted,
                onClickArrow = onClickArrow,
            )
        } else {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
            ) {
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    text = "No task found in this date, please add your first task with the + symbol down belong",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
fun ListaFechas(
    dates: List<DateState>,
    onClickDate:(DateState) -> Unit = {},
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
    onDeleted: (TaskModel) -> Unit,
    onClickArrow: (TaskModel) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(itemList) { item ->
                SwipeToDeleteContainer(
                    item = item,
                    onDelete = onDeleted,
                    content = {
                        FilledIconottomCustom(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(
                                        horizontal = 16.dp,
                                    ).background(
                                        color = Color.Transparent,
                                    ),
                            onClickArrow = {
                                onClickArrow(item)
                            },
                            itemTask = item
                        )
                    },
                )
            }
        }

    }
}

@Composable
fun FilledIconottomCustom(
    modifier: Modifier = Modifier,
    onClickArrow: (TaskModel) -> Unit,
    itemTask: TaskModel?,
    colorContent: Color = Color.White,
) {
    FilledIconButton(
        modifier = modifier,
        onClick ={
            onClickArrow(itemTask ?: TaskModel())
        },
        shape = RoundedCornerShape(30)
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
            onDeleted = {},
            onClickArrow = {},
        )
    }
}