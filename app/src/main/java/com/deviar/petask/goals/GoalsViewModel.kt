package com.deviar.petask.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deviar.petask.goals.usecase.GetGoalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private var getGoalsUseCase: GetGoalsUseCase
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


    fun onAddNewGoal (){}
    fun onDeleteGoal (){}
    fun updateGoalName(){}



}
