package com.deviar.petask.common.database.data

import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.database.domain.dao.TaskDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
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
        ):  Flow<List<TaskModel>?>? {
            val userId = getCurrentUserId() ?: return null
            val calendar = Calendar.getInstance().apply {
                time = selectedDate
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val startOfDay = calendar.timeInMillis
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            val endOfDay = calendar.timeInMillis
            return  taskDao.getTasksByDateAndUserId(
                userId = userId,
                startOfDay = startOfDay,
                endOfDay = endOfDay,
            )
        }

        suspend fun deleteTask(idTask: String) {
            taskDao.deleteByTaskId(idTask)
        }

        suspend fun updateTask(task: TaskModel){
            taskDao.update(task)
        }
}
