package com.deviar.petask.tasks.domain

data class TasksState (
    var datesUpcoming: List<String> = arrayListOf(),
    var taskList: List<String> = arrayListOf(),
    var selectedDate: String? = "23/02/1990",
)