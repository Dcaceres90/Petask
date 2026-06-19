package com.deviar.petask.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.deviar.petask.common.ui.theme.PetaskTheme
import kotlin.String

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    viewModel: TaskViewModel,
) {
    var selectedDate by remember { mutableStateOf(viewModel.getCurrentDate()) }
    val dates = viewModel.getUpcomingDates()
    val itemList = remember { mutableStateListOf<String>() }
    TaskStructureScreen(
        selectedDate = selectedDate,
        dates = dates,
        itemList = itemList,
        onClickDate = { date ->
            selectedDate = date
        }
    )
}

@Composable
fun TaskStructureScreen(
    selectedDate: String,
    dates: List<String>,
    itemList: List<String>,
    onClickDate:(String) -> Unit = {}
) {
    Column {
        ListaFechas(
            selectedDate = selectedDate,
            dates = dates,
            onClickDate = onClickDate
        )

        ListaTask(
            selectedDate = selectedDate,
            itemList = itemList,
        )
    }
}

@Composable
fun ListaFechas(
    selectedDate: String,
    dates: List<String>,
    onClickDate:(String) -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Fecha seleccionada: $selectedDate")

        Spacer(modifier = Modifier.height(20.dp))

        // Mostrar los botones para cada fecha
        dates.forEach { date ->
            Button(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                onClick = {
                    onClickDate(date)
                }
            ) {
                Text(text = date)
            }
        }
    }
}

@Composable
fun ListaTask(
    selectedDate: String,
    itemList: List<String>,
) {
    Column {
        Button(onClick = { /* Mostrar DatePickerDialog aquí */ }) {
            Text("Seleccionar Fecha: ${selectedDate}")
        }

        LazyColumn {
            items(itemList) { item ->
                Text(item, modifier = Modifier.padding(8.dp))
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
            dates = listOf(),
            selectedDate = "23/02/1990",
            itemList = listOf(),
            onClickDate = {},
        )
    }
}