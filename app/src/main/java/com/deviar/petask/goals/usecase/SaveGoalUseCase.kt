package com.deviar.petask.goals.usecase

import com.deviar.petask.common.database.data.model.GoalModel
import com.deviar.petask.common.database.data.GoalRepository
import com.deviar.petask.common.database.data.model.GoalType
import javax.inject.Inject

class SaveGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(text: String, goalType: GoalType){
        goalRepository.saveGoal(text, goalType)
    }
}