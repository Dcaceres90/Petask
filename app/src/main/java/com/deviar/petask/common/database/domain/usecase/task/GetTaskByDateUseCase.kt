package com.deviar.petask.common.database.domain.usecase.task

import com.deviar.petask.common.database.data.TaskRepository
import com.deviar.petask.common.database.data.model.TaskModel
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class GetTaskByDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(selectedDate: Date): Flow<List<TaskModel?>>? {
        return taskRepository.getTasks(selectedDate)
    }
}