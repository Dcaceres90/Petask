package com.deviar.petask.goals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.deviar.petask.common.database.data.model.GoalType
import com.deviar.petask.common.ui.components.button.FloatingButton
import com.deviar.petask.common.ui.components.dialog.AddDialog
import com.deviar.petask.goals.state.GoalUiState
import com.deviar.petask.goals.usecase.GoalsDialogContent

@Composable
fun GoalsScreen(
    modifier: Modifier,
    goalsViewModel: GoalsViewModel
) {
    val uiState by goalsViewModel.iuState.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }

    val weeklyGoals = uiState.goals.filter {
        it.goalType == GoalType.WEEKLY
    }

    val monthlyGoals = uiState.goals.filter {
        it.goalType == GoalType.MONTHLY
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
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

            Text("Weekly Goals")

            weeklyGoals.forEach { goal ->
                GoalItem(
                    goal = goal,
                    onCheckedChange = { isComplete ->
                        goalsViewModel.updateIsCompleted(
                            goalId = goal.goalId,
                            isComplete = isComplete
                        )
                    }
                )
            }

            Spacer(Modifier.height(30.dp))

            Text("Monthly Goals")


            monthlyGoals.forEach { goal ->
                GoalItem(
                    goal = goal,
                    onCheckedChange = { isComplete ->
                        goalsViewModel.updateIsCompleted(
                            goalId = goal.goalId,
                            isComplete = isComplete
                        )
                    }
                )
            }

            Spacer(Modifier.height(30.dp))

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
                            goalsViewModel.createGoal(
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


