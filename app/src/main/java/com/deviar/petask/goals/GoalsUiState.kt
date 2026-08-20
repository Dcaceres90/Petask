package com.deviar.petask.goals

data class GoalsUiState (
    val goals: List<GoalUiState> = emptyList(),
    val isLoading: Boolean = true
)