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
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.common.ui.components.dialog.FormAlertDialog
import com.deviar.petask.common.ui.components.button.ButtonFloating
import com.deviar.petask.tasks.domain.TasksState
import java.util.Date
import kotlin.String

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    viewModel: TaskViewModel,
) {
    var showFormAlertDialog by remember { mutableStateOf(false) }
    val uiTasksState by viewModel.uiTasksState.collectAsStateWithLifecycle()
    val newTaskFormState by viewModel.newTaskFormState.collectAsStateWithLifecycle()
    viewModel.updateScreenDatesList(
        datesUpcoming = viewModel.getUpcomingDates(),
    )
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
                        selectedDateLong = Date().time,
                        onDismiss = {
                                showFormAlertDialog = false
                            },
                        onDateSelected = {
                            // Handle the selected date
                            viewModel.updateNewTaskFormScreenDateToDoString(
                                selectedDate = it?.toString() ?: viewModel.getDateSelectedFormat(),
                            )

                            viewModel.updateNewTaskFormScreenDateToDo(
                                selectedDate = Date(it ?: 0L ),
                            )


                        },
                        onValueChangedText = {
                            viewModel.updateTitleNewTaskFormScreen(title = it)
                        },
                        onClickConfirm = { newTask ->
                            // Enviamos los datos capturados a la función superior
                            viewModel.insertTaskDataBase(newTask)
                            showFormAlertDialog = false
                        },
                    )
                }
                TaskStructureScreen(
                    uiTasksState,
                    onClickDate = { date ->
                        viewModel.updateScreenSelectedDate(
                            selectedDate = date ?: viewModel.getDateSelectedFormat(),
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
            selectedDate = tasksState.selectedDate,
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
    selectedDate: String?,
    itemList: List<String>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilledIconottomCustom(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                ),
            onClickArrow = {
                /* Mostrar DatePickerDialog aquí */
            },
            selectedDate = selectedDate
        )

        LazyColumn {
            items(itemList) { item ->
                Text(item, modifier = Modifier.padding(8.dp))
            }
        }

    }
}

@Composable
fun  FilledIconottomCustom(
    modifier: Modifier = Modifier,
    onClickArrow: () -> Unit,
    selectedDate: String?,
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
            Text(
                "Seleccionar Fecha: $selectedDate",
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