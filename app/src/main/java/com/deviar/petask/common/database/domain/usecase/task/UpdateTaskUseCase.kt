package com.deviar.petask.common.database.domain.usecase.task

import com.deviar.petask.common.database.data.TaskRepository
import com.deviar.petask.common.database.data.model.TaskModel
import javax.inject.Inject

class UpdateTaskUseCase@Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: TaskModel) {
        taskRepository.updateTask(task)
    }
}