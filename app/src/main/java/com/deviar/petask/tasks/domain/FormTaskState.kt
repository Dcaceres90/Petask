package com.deviar.petask.tasks.domain
data class FormTaskState (
    var title: String = "Título de la tarea",
    var taskEdit: TaskState = TaskState(),
    var showDialog: Boolean = false,
)