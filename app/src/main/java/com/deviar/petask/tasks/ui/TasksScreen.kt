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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.ui.components.dialog.FormAlertDialog
import com.deviar.petask.common.ui.components.button.ButtonFloating
import com.deviar.petask.common.utils.LevelDificult
import com.deviar.petask.common.utils.SwipeToDeleteContainer
import com.deviar.petask.tasks.domain.DateState
import com.deviar.petask.tasks.domain.NewTaskFormState
import com.deviar.petask.tasks.domain.TasksState
import com.deviar.petask.tasks.domain.TasksUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.String

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    viewModel: TaskViewModel,
) {
    //TODO Mej orar las animaciones
    // Revizar la actualizacion de una lista a otra
    // No esta editando y pensar que paner cuando no hay tareas en ese dia

    var showFormAlertDialog by remember { mutableStateOf(false) }
    var isEditTask by remember { mutableStateOf(false) }
    val uiTasksState by viewModel.uiTasksState.collectAsStateWithLifecycle()
    val uiTasks by viewModel.uiTasks.collectAsStateWithLifecycle()
    val newTaskFormState by viewModel.newTaskFormState.collectAsStateWithLifecycle()

    viewModel.updateScreenDatesList(
        datesUpcoming = viewModel.getUpcomingDates(),
    )
    viewModel.collectedSucessTask()
    viewModel.getUID()

    var selectedDateMillis by remember {
        mutableLongStateOf(value = uiTasksState.selectedDate.date.time)
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis
    )

    viewModel.getTasksByDate(selectedDate = Date(uiTasksState.selectedDate.date.time))

    when(uiTasks) {
        is TasksUiState.Loading -> {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is TasksUiState.Success -> {
            /* var selectedDateMillis by remember {
                mutableLongStateOf(value = uiTasks.selectedDate.date.time)
            }

            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = selectedDateMillis
            )

            viewModel.getTasksByDate(Date(uiTasks.selectedDate.date.time))*/

        }

        is TasksUiState.Error -> {

        }
    }
    SuccessTasksListScreen(
        viewModel = viewModel,
        showFormAlertDialog = showFormAlertDialog,
        newTaskFormState = newTaskFormState,
        datePickerState = datePickerState,
        selectedDateMillis = selectedDateMillis,
        uiTasksState = uiTasksState,
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
            viewModel.updateNewTaskFormScreenText(it.text)
            viewModel.updateLevelDificultNewTaskFormScreen(it.levelDificult)
            viewModel.updateNewTaskFormScreenDateToDo(it.toDoDate)
            val displayDate = it.toDoDate.time.let { time ->
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                sdf.format(Date(time))
            } ?: ""
            viewModel.updateNewTaskFormScreenDateToDoString(displayDate)
            isEditTask = true
            showFormAlertDialog = true
        },
    )
}

@Composable
fun SuccessTasksListScreen(
    viewModel: TaskViewModel,
    showFormAlertDialog: Boolean,
    newTaskFormState: NewTaskFormState,
    datePickerState: DatePickerState,
    selectedDateMillis: Long,
    uiTasksState: TasksState,
    onClickFloating: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onClickConfirmDateSpicker: (Long) -> Unit,
    onClickConfirm: (TaskModel) -> Unit,
    onClickArrow: (TaskModel) -> Unit,
) {
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
                    tasksState = uiTasksState,
                    onClickDate = { date ->
                        viewModel.updateScreenSelectedDate(
                            selectedDateList = date,
                        )
                    },
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
    tasksState: TasksState,
    onDeleted: (TaskModel) -> Unit,
    onClickDate:(DateState) -> Unit = {},
    onClickArrow:(TaskModel) -> Unit,
) {
    Column {
        ListaFechas(
            dates = tasksState.datesUpcoming,
            onClickDate = onClickDate
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (tasksState.taskList.isNotEmpty()) {
            ListaTask(
                itemList = tasksState.taskList,
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
            TasksState(),
            onClickDate = {},
            onDeleted = {},
            onClickArrow = {},
        )
    }
}