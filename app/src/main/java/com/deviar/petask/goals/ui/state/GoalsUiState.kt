package com.deviar.petask.goals.ui.state

data class GoalsUiState (
    val goals: List<GoalUiState> = emptyList(),
    val isLoading: Boolean = true
)