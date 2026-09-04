package com.deviar.petask.goals.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.common.database.data.model.GoalType
import com.deviar.petask.common.ui.components.button.FloatingButton
import com.deviar.petask.common.ui.components.dialog.AddDialog
import com.deviar.petask.goals.ui.component.GoalItem
import com.deviar.petask.goals.ui.component.GoalsDialogContent
import com.deviar.petask.goals.ui.state.GoalUiState


// TODO fix coins bug
@Composable
fun GoalsScreen(
    modifier: Modifier,
    goalsViewModel: GoalsViewModel
) {
    val uiState by goalsViewModel.iuState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var selectedGoal by remember { mutableStateOf<GoalUiState?>(null) }
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
                    if (uiState.goals.size >= 6) {
                        Toast.makeText(
                            context,
                            "You can only add up to 6 goals in total",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        showDialog = true
                    }
                }
            )
        },
    ) { paddingValues ->

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {

            Column(modifier = modifier.padding(paddingValues)) {

                Text(
                    text = "Weekly Goals",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                weeklyGoals.forEach { goal ->
                    GoalItem(
                        goal = goal,
                        onCheckedChange = { isComplete ->
                            goalsViewModel.updateIsCompleted(
                                goal = goal,
                                isComplete = isComplete
                            )
                        },
                        onClick = {
                            selectedGoal = goal
                            showDialog = true
                        },
                        onDeleteClick = { goalsViewModel.deleteGoal(goal.goalId) }
                    )
                }

                Spacer(Modifier.height(30.dp))

                Text(
                    text = "Monthly Goals",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )


                monthlyGoals.forEach { goal ->
                    GoalItem(
                        goal = goal,
                        onCheckedChange = { isComplete ->
                            goalsViewModel.updateIsCompleted(
                                goal = goal,
                                isComplete = isComplete
                            )
                        },
                        onClick = {
                            selectedGoal = goal
                            showDialog = true
                        },
                        onDeleteClick = { goalsViewModel.deleteGoal(goal.goalId) }
                    )
                }

            }
        }

        if (showDialog) {
            AddDialog(
                onDismiss = { showDialog = false
                    selectedGoal = null },
                onContent = {
                    GoalsDialogContent(
                        goal = selectedGoal,
                        onDismiss = {
                            showDialog = false
                            selectedGoal = null
                        },
                        onAddGoal = { text, goalType ->
                            if (selectedGoal == null) {

                                goalsViewModel.createGoal(
                                    text = text,
                                    goalType = goalType
                                )
                            } else {
                                goalsViewModel.updateGoal(
                                    goalId = selectedGoal!!.goalId,
                                    text = text,
                                    goalType = goalType,
                                    isComplete = selectedGoal!!.isComplete
                                )
                            }
                        }
                    )
                }
            )
        }

    }


}


