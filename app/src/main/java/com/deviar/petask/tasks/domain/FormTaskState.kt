package com.deviar.petask.tasks.domain
data class FormTaskState (
    var title: String = "",
    var taskEdit: TaskState = TaskState(),
    var showDialog: Boolean = false,
    var isEditTask: Boolean = false,
    var isEmptyTitleTask: Boolean = false,
)