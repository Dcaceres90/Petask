package com.deviar.petask.tasks.domain

import com.deviar.petask.common.database.data.model.TaskModel
import java.util.Date

data class TasksState (
    var datesUpcoming: List<DateState> = arrayListOf(),
    var taskList: List<TaskModel> = arrayListOf(),
    var selectedDate: DateState =
        DateState(
            date = Date(),
            showDate = "23/02/1990",
        ),
)