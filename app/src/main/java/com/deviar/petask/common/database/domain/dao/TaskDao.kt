package com.deviar.petask.common.database.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.deviar.petask.common.database.data.model.TaskModel
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskModel)

    @Query("SELECT * FROM TaskModel WHERE idUser = :userId")
    fun getTaskByIdUser(userId: String): Flow<List<TaskModel>>

    @Update
    suspend fun update(task: TaskModel)

    @Query("SELECT * FROM TaskModel WHERE idUser = :userId AND toDoDate >= :startOfDay AND toDoDate < :endOfDay")
    fun getTasksByDateAndUserId(userId: String, startOfDay: Long, endOfDay: Long):  Flow<List<TaskModel>>

    @Query("DELETE FROM TaskModel WHERE idTask = :taskId")
    fun deleteByTaskId(taskId: String)
}