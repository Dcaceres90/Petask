package com.deviar.petask.goals.usecase

import com.deviar.petask.common.database.data.GoalRepository
import javax.inject.Inject

class UpdateIsCompletedGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(
        goalId: String,
        isComplete: Boolean
    ) {
        goalRepository.updateIsCompletedGoal(
            goalId = goalId,
            isComplete = isComplete
        )
    }
}