package com.deviar.petask.goals.usecase

import com.deviar.petask.common.database.data.model.GoalModel
import com.deviar.petask.common.database.data.GoalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoalsUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    operator fun invoke(): Flow<List<GoalModel>> {
        return goalRepository.getGoal()
    }
}