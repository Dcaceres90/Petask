package com.deviar.petask.tasks.domain

import com.deviar.petask.common.database.data.model.TaskModel

data class TasksState (
    var datesUpcoming: List<DateState> = arrayListOf(),
    var taskList: List<TaskModel> = arrayListOf(),
    var selectedDate: String? = "23/02/1990",
)