package com.deviar.petask.goals.usecase

import com.deviar.petask.common.database.data.GoalRepository
import javax.inject.Inject

class DeleteGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(
        goalId: String
    ){
        goalRepository.deleteGoal(goalId)
    }
}