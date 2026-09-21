package com.deviar.petask.tasks.domain

import com.deviar.petask.common.database.data.model.TaskModel
import java.util.Date

sealed class TasksUiState {
    object Loading: TasksUiState()
    data class Success(
        val datesUpcoming: List<DateState> = arrayListOf(),
        val taskList: List<TaskModel> = arrayListOf(),
        val selectedDate: DateState =
            DateState(
                date = Date(),
                showDate = "23/02/1990",
            ),
    ): TasksUiState()
    data class Error(val menssage: String): TasksUiState()
}