package com.deviar.petask.tasks.domain

import com.deviar.petask.common.database.data.model.TaskModel
import java.util.Date

sealed class TasksListUiState {
    object Loading: TasksListUiState()
    data class Success(
        val taskList: List<TaskModel> = arrayListOf(),
    ): TasksListUiState()
    data class Error(val message: String): TasksListUiState()
}