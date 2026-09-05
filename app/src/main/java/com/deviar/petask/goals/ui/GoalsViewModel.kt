package com.deviar.petask.goals.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deviar.petask.common.database.data.model.GoalType
import com.deviar.petask.common.database.domain.usecase.UpdateCoinsUseCase
import com.deviar.petask.common.database.domain.usecase.UpdateExpUseCase
import com.deviar.petask.goals.ui.state.GoalUiState
import com.deviar.petask.goals.ui.state.GoalsUiState
import com.deviar.petask.goals.usecase.DeleteGoalUseCase
import com.deviar.petask.goals.usecase.GetGoalsUseCase
import com.deviar.petask.goals.usecase.SaveGoalUseCase
import com.deviar.petask.goals.usecase.UpdateGoalUseCase
import com.deviar.petask.goals.usecase.UpdateIsCompletedGoalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private var getGoalsUseCase: GetGoalsUseCase,
    private val saveGoalUseCase: SaveGoalUseCase,
    private val updateIsCompletedGoalUseCase: UpdateIsCompletedGoalUseCase,
    private val updateGoalUseCase: UpdateGoalUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase,
    private val updateExpUseCase: UpdateExpUseCase,
    private val updateCoinsUseCase: UpdateCoinsUseCase
)  : ViewModel() {

    val _uiState = MutableStateFlow(GoalsUiState())
    val iuState: StateFlow<GoalsUiState> = _uiState.asStateFlow()


    init {
        getGoals()
    }

    private fun getGoals() {
        viewModelScope.launch {
            getGoalsUseCase()
                .collect { goals ->

                    _uiState.update { currentState ->
                        currentState.copy(
                            goals = goals.map { goal ->
                                GoalUiState(
                                    goalId = goal.goalId,
                                    text = goal.text,
                                    isComplete = goal.isComplete,
                                    goalType = goal.goalType
                                )
                            },
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun createGoal(
        text: String,
        goalType: GoalType
    ){

        viewModelScope.launch {
            saveGoalUseCase(
                    text = text,
                    goalType = goalType
            )

        }
    }

    fun updateIsCompleted(
        goal: GoalUiState,
        isComplete: Boolean,
    ) {
        viewModelScope.launch {
            updateIsCompletedGoalUseCase(
                goalId = goal.goalId,
                isComplete = isComplete
            )
            if (isComplete) {
                updateExpUseCase(goal.goalType.exp)
                updateCoinsUseCase(goal.goalType.coinValue)
            }
            if (!isComplete) {
                updateExpUseCase(-goal.goalType.exp)
                updateCoinsUseCase(-goal.goalType.coinValue)
            }
        }

    }

    fun updateGoal(
        goalId: String,
        text: String,
        goalType: GoalType,
        isComplete: Boolean
    ){

        viewModelScope.launch {
            updateGoalUseCase(
                goalId = goalId,
                text = text,
                goalType = goalType,
                isComplete = isComplete
            )
        }
    }

    fun deleteGoal (goalId: String){
        viewModelScope.launch {
            deleteGoalUseCase(goalId)
        }
    }



}