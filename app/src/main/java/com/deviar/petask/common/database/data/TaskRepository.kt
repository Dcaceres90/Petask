package com.deviar.petask.common.database.data

import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.database.domain.dao.TaskDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class TaskRepository
    @Inject constructor(
        private val taskDao: TaskDao,
        private val auth: FirebaseAuth
    ) {

        fun getCurrentUserId(): String? {
            return auth.currentUser?.uid
        }

        suspend fun saveTask(task: TaskModel) {
            taskDao.insertTask(task)
        }

        suspend fun getTasks(
            selectedDate: Date,
        ): Flow<List<TaskModel?>>? {
            val userId = getCurrentUserId() ?: return null
            return taskDao.getTasksByDateAndUserId(userId, selectedDate)
        }

        suspend fun deleteTask(idTask: String) {
            taskDao.deleteByTaskId(idTask)
        }

        suspend fun updateTask(task: TaskModel){
            taskDao.update(task)
        }
}
