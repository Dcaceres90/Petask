package com.deviar.petask.task

import ads_mobile_sdk.h6
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
import androidx.compose.material3.MaterialTheme
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
import com.deviar.petask.common.ui.theme.PetaskTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(
    getCurrentDate: (Unit) -> String,
    getUpcomingDates: (Unit) -> List<String>,
) {
    var selectedDate by remember { mutableStateOf(getCurrentDate(Unit)) }
    val dates = getUpcomingDates(Unit)
    val itemList = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Fecha seleccionada: $selectedDate", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(20.dp))

        // Mostrar los botones para cada fecha
        dates.forEach { date ->
            Button(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                onClick = {
                    selectedDate = date
                }
            ) {
                Text(text = date)
            }
        }
    }

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
fun PreviewTaskScreen() {
    PetaskTheme {
        TaskScreen(
            getCurrentDate = {
                return ""
            },
            getUpcomingDates = {},
        )
    }
}