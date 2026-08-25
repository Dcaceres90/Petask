package com.deviar.petask.goals.usecase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deviar.petask.common.database.data.model.GoalType
import com.deviar.petask.common.ui.components.button.AddMenuButton
import com.deviar.petask.goals.LocalNestedDialogState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GoalsDialogContent(
    onDismiss: () -> Unit,
    onAddGoal: (String, GoalType) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var selectedGoalType by remember { mutableStateOf(GoalType.WEEKLY) }

    var showTypeDialog by remember { mutableStateOf(false) }
    val nestedDialogState = LocalNestedDialogState.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Enfoque inicial
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    // Manejo del cierre del dialog secundario
    LaunchedEffect(showTypeDialog) {
        if (!showTypeDialog) {
            nestedDialogState.isOpen = false
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    BasicTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .focusRequester(focusRequester),
        textStyle = MaterialTheme.typography.bodyLarge,
        decorationBox = { innerTextField ->
            if (text.isEmpty()) {
                Text(
                    text = "Nueva meta",
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            }
            innerTextField()
        }
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AddMenuButton(
            text = selectedGoalType.name,
            onClick = {
                nestedDialogState.isOpen = true
                showTypeDialog = true
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            onAddGoal(text, selectedGoalType)
            onDismiss()

        }) { Text("Add") }

        if (showTypeDialog) {
            AlertDialog(
                onDismissRequest = {
                    showTypeDialog = false // No hacemos nada acá, el LaunchedEffect se encarga
                },
                title = { Text("Tipo de objetivo") },
                text = {
                    Column {
                        TextButton(
                            onClick = {
                                selectedGoalType = GoalType.WEEKLY
                                showTypeDialog = false
                            }
                        ) { Text("Weekly") }

                        TextButton(
                            onClick = {
                                selectedGoalType = GoalType.MONTHLY
                                showTypeDialog = false
                            }
                        ) { Text("Monthly") }
                    }
                },
                confirmButton = {}
            )
        }
    }
}