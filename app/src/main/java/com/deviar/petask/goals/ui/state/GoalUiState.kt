package com.deviar.petask.goals.ui.state

import com.deviar.petask.common.database.data.model.GoalType

data class GoalUiState (
    val goalId: String = "",
    val text: String = "",
    val isComplete: Boolean = false,
    val goalType: GoalType = GoalType.WEEKLY
)