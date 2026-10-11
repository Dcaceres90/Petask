package com.deviar.petask.goals.usecase

import com.deviar.petask.common.database.data.GoalRepository
import com.deviar.petask.common.database.data.model.GoalType
import javax.inject.Inject

class UpdateGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(
        goalId: String,
        text: String,
        goalType: GoalType,
        isComplete: Boolean
    ) {
        goalRepository.updateGoal(
            goalId = goalId,
            text = text,
            goalType = goalType,
            isComplete = isComplete
        )
    }

}