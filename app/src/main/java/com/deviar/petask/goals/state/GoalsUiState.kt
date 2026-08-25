package com.deviar.petask.goals.state

data class GoalsUiState (
    val goals: List<GoalUiState> = emptyList(),
    val isLoading: Boolean = true
)