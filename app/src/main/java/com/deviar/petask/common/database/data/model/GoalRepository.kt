package com.deviar.petask.common.database.data.model

import com.deviar.petask.common.database.domain.dao.GoalDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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

    suspend fun saveGoal(goal: GoalModel) {
        goalDao.insertGoal(goal)
    }

    suspend fun deleteGoal(goalId: String){
        goalDao.deleteByGoalId(goalId)
    }
}