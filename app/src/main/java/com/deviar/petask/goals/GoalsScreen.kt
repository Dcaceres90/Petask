package com.deviar.petask.goals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.common.ui.components.button.FloatingButton
import com.deviar.petask.common.ui.components.dialog.AddDialog
import com.deviar.petask.goals.usecase.GoalsDialogContent

@Composable
fun GoalsScreen(
    modifier: Modifier,
    goalsViewModel: GoalsViewModel
) {
    val uiState by goalsViewModel.iuState.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingButton(
                modifier = Modifier.padding(
                    end = 20.dp,
                    bottom = 70.dp,
                ),
                onClickFloating = {
                    showDialog = true
                }
            )
        },
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {

            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Cantidad de objetivos: ${uiState.goals.size}")
            }

        }

        if (showDialog) {
            AddDialog(
                onDismiss = { showDialog = false },
                onContent = {
                    GoalsDialogContent(
                        onDismiss = { showDialog = false },
                        onAddGoal = { text, goalType ->
                            goalsViewModel.createTestGoal(
                                text = text,
                                goalType = goalType
                            )
                        }
                    )
                }
            )
        }

    }


}


