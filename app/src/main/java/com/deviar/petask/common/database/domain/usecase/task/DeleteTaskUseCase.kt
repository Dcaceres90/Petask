package com.deviar.petask.common.database.domain.usecase.task

import com.deviar.petask.common.database.data.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase@Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: String) {
        taskRepository.deleteTask(taskId)
    }
}