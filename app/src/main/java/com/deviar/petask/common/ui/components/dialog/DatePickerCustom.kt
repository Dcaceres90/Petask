package com.deviar.petask.common.ui.components.dialog

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogCustom(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    //  val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
    )

    LaunchedEffect(datePickerState.selectedDateMillis) {
       // viewModel.onDateSelected(datePickerState.selectedDateMillis)
    }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}