package com.deviar.petask.common.database.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deviar.petask.common.database.data.model.GoalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal : GoalModel)

    @Query("SELECT * FROM GoalModel WHERE userId = :userId")
    fun getGoalByIdUser(userId: String): Flow<List<GoalModel>>

    @Query("DELETE FROM goalmodel WHERE goalId = :goalId")
    suspend fun deleteByGoalId(goalId: String)


}