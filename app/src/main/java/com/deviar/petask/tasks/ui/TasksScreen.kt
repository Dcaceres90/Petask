package com.deviar.petask.tasks.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.deviar.petask.common.ui.components.listas.MiListaHorizontal
import com.deviar.petask.common.ui.theme.PetaskTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.vector.ImageVector
import com.deviar.petask.common.ui.components.button.ButtonFloating
import kotlin.String

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    viewModel: TaskViewModel,
) {
    var selectedDate by remember { mutableStateOf(viewModel.getCurrentDate()) }
    val dates = viewModel.getUpcomingDates()
    val itemList = remember { mutableStateListOf<String>() }
    Scaffold(
        floatingActionButton = {
            ButtonFloating(
                onClickFloating = {}
            )
        }
    ) { paddingValues ->
        // contenido de la pantalla
        Column(modifier = Modifier.padding(paddingValues)) {
            TaskStructureScreen(
                selectedDate = selectedDate,
                dates = dates,
                itemList = itemList,
                onClickDate = { date ->
                    selectedDate = date
                }
            )
        }
    }

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
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Fecha seleccionada: $selectedDate")

        Spacer(modifier = Modifier.height(20.dp))
        // Mostrar los botones para cada fecha
        Row() {
            MiListaHorizontal(
                items = dates,
                onClickItem = onClickDate
            )
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
            Text("Seleccionar Fecha: $selectedDate")
        }

        LazyColumn {
            items(itemList) { item ->
                Text(item, modifier = Modifier.padding(8.dp))
            }
        }

    }
}

@Composable
fun IconBottomCustom(
    onClickArrow: () -> Unit,
    imageVector: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
) {
    IconButton(
        onClick = onClickArrow,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Regresar"
        )
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