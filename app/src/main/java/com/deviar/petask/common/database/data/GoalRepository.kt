package com.deviar.petask.common.database.data

import com.deviar.petask.common.database.data.model.GoalModel
import com.deviar.petask.common.database.data.model.GoalType
import com.deviar.petask.common.database.domain.dao.GoalDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject

class GoalRepository @Inject constructor(
    private val goalDao: GoalDao,
    private val auth: FirebaseAuth
) {

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

     fun getGoal(): Flow<List<GoalModel>> {
        val userId = getCurrentUserId()
        return if (userId != null) {
            goalDao.getGoalByIdUser(userId)
        } else {
            flowOf(emptyList())
        }
    }

    suspend fun saveGoal(text: String, goalType: GoalType) {
        val userId = getCurrentUserId() ?: return

        val goal = GoalModel(
            goalId = UUID.randomUUID().toString(),
            userId = userId,
            text = text,
            isComplete = false,
            goalType = goalType
        )

        goalDao.insertGoal(goal)
    }

    suspend fun deleteGoal(goalId: String){
        goalDao.deleteByGoalId(goalId)
    }
}