package com.deviar.petask.common.database.domain.usecase.task

import com.deviar.petask.common.database.data.TaskRepository
import com.deviar.petask.common.database.data.model.TaskModel
import javax.inject.Inject

class InsertTaskUseCase@Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(newTask: TaskModel) {
        taskRepository.saveTask(newTask)
    }
}