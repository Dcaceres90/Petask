package com.deviar.petask.tasks.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.ui.components.dialog.FormAlertDialog
import com.deviar.petask.common.ui.components.button.ButtonFloating
import com.deviar.petask.common.ui.theme.GoldCoin
import com.deviar.petask.common.ui.theme.Secondary
import com.deviar.petask.common.utils.LevelDificult
import com.deviar.petask.common.utils.SwipeToDeleteContainer
import com.deviar.petask.tasks.domain.DateState
import com.deviar.petask.tasks.domain.FormTaskState
import com.deviar.petask.tasks.domain.HorizotalListState
import com.deviar.petask.tasks.domain.TaskState
import kotlinx.coroutines.launch
import com.deviar.petask.tasks.domain.TasksListUiState
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    viewModel: TaskViewModel,
    horizotalListState: HorizotalListState,
    uiTasks: TasksListUiState,
) {
    //TODO
    // Mejorar las animaciones
    // Formulario que el titulo se borre
    // Cambiar los nombres de los state por mas entendibles
    // Cuando se borre

    var showFormAlertDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedDateMillis by remember {
        mutableLongStateOf(value = horizotalListState.selectedDate.date.time)
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis
    )

    val newTaskFormState by viewModel.formTaskState.collectAsStateWithLifecycle()

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
                    viewModel.updateScreenDatesList(
                        datesUpcoming = viewModel.getUpcomingDates(),
                    )

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
                        formTaskState = newTaskFormState,
                        datePickerState = datePickerState,
                        selectedDateMillis = selectedDateMillis,
                        onClickFloating = {
                            showFormAlertDialog = true
                            viewModel.updateIsEditTask(false)
                            viewModel.updateisEmptyTitleTask(true)
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
                            if (!newTaskFormState.isEditTask) {
                                viewModel.insertTaskDataBase(newTask)
                            } else {
                                viewModel.updateTaskDataBase(newTask)
                            }
                            showFormAlertDialog = false
                        },
                        snackbarHostState = snackbarHostState,
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
                            viewModel.updateIsEditTask(true)
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
    formTaskState: FormTaskState,
    datePickerState: DatePickerState,
    selectedDateMillis: Long,
    snackbarHostState: SnackbarHostState,
    onClickFloating: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onClickConfirmDateSpicker: (Long) -> Unit,
    onClickConfirm: (TaskModel) -> Unit,
    onClickArrow: (TaskModel) -> Unit,
) {
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier.padding(
            start = 10.dp,
            end = 10.dp
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
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { paddingValues ->
            // contenido de la pantalla
            Column(modifier = Modifier.padding(paddingValues)) {
                if (showFormAlertDialog) {
                    FormAlertDialog(
                        formTaskState = formTaskState,
                        idUser = viewModel.uuidState.value,
                        selectedDateLong = selectedDateMillis,
                        datePickerState = datePickerState,
                        onDismiss = onDismiss,
                        onValueChangedText = {
                            viewModel.updateisEmptyTitleTask(false)
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
                        viewModel.getTasksByDate(
                            selectedDate = Date(viewModel.horizotalListState.value.selectedDate.date.time),
                        )
                    },
                    onClickArrow = onClickArrow,
                    onCheckedChange = {
                        if (it.isComplete) {
                            viewModel.updateNewTaskIsComplete(it.isComplete)
                            viewModel.updateCoins(it.levelDificult.coinValue)
                            viewModel.deleteTaskDataBase(
                                taskId = it.idTask,
                            )
                            // snackBar()
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Elemento eliminado",
                                    actionLabel = "Deshacer",
                                    duration = SnackbarDuration.Long // o Indefinite para forzar la acción
                                )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        // El usuario pulsó "Deshacer": restauramos el elemento
                                        val taskDeleted = TaskModel(
                                            idTask = it.idTask,
                                            idUser = it.idUser,
                                            text = it.text,
                                            isComplete = false,
                                            levelDificult = it.levelDificult,
                                            toDoDate = it.toDoDate,
                                        )
                                        viewModel.insertTaskDataBase(taskDeleted)
                                        viewModel.updateNewTaskIsComplete(taskDeleted.isComplete)
                                        viewModel.updateCoins(-taskDeleted.levelDificult.coinValue)
                                    }

                                    SnackbarResult.Dismissed -> {
                                        // El usuario descartó la snackbar sin deshacer
                                    }
                                }
                            }
                        }
                    },
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
    onCheckedChange: (TaskModel) -> Unit,
) {
    Column {
        if (taskList.isNotEmpty()) {
            ListaTask(
                itemList = taskList,
                onDeleted = onDeleted,
                onClickArrow = onClickArrow,
                onCheckedChange = onCheckedChange,
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
    onCheckedChange: (TaskModel) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        LazyColumn {
            itemsIndexed(itemList) {index, item ->
                SwipeToDeleteContainer(
                    item = item,
                    onDelete = onDeleted,
                    content = {
                        FilledIconottomCustom(
                            index = index.toLong(),
                            modifier =
                                Modifier
                                    .background(
                                        color = Color.Transparent,
                                    )
                                    .fillMaxSize(),
                            onClickArrow = {
                                onClickArrow(item)
                            },
                            itemTask = item,
                            onCheckedChange = {
                                val taskModel = TaskModel(
                                    idTask = item.idTask,
                                    idUser = item.idUser,
                                    text = item.text,
                                    isComplete = it,
                                    levelDificult = item.levelDificult,
                                    toDoDate = item.toDoDate,
                                )
                                onCheckedChange(taskModel)
                            },
                        )
                    },
                )
            }
        }

    }
}

@Composable
fun FilledIconottomCustom(
    index: Long,
    modifier: Modifier = Modifier,
    onClickArrow: (TaskModel) -> Unit,
    itemTask: TaskModel?,
    colorContent: Color = Color.Black,
    onCheckedChange: (Boolean) -> Unit,
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(index * 50L)
        visible = true
    }

    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else -100f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = "offset",
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
        ),
        label = "alpha",
    )

    FilledIconButton(
        modifier =
            modifier.graphicsLayer {
                translationY = offsetY
                this.alpha = alpha
            }
        ,
        onClick ={
            onClickArrow(itemTask ?: TaskModel())
        },
        shape = RoundedCornerShape(30),
        colors = IconButtonColors(
            containerColor = Secondary,
            contentColor = colorContent,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray,
        ),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = itemTask?.isComplete == true,
                onCheckedChange = onCheckedChange,
            )
            Column {
                Row() {
                    Text(
                        modifier = Modifier.weight(4F),
                        text = "${itemTask?.text}",
                        color = colorContent,
                    )
                    Text(
                        modifier = Modifier.weight(1F),
                        text = "+ ${itemTask?.levelDificult?.coinValue}",
                        color = GoldCoin,
                        fontWeight = FontWeight.Bold,
                    )
                }
                // Todo hacer padin
                Text(
                    text = "${itemTask?.levelDificult?.name}",
                    color = colorContent,
                    fontSize = 10.sp,
                )
            }
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
            taskList = arrayListOf(),
            onDeleted = {},
            onClickArrow = {},
            onCheckedChange = {},
        )
    }
}