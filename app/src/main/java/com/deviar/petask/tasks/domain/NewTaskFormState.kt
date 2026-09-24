package com.deviar.petask.tasks.domain
data class NewTaskFormState (
    var title: String = "Título de la tarea",
    var taskEdit: TaskState = TaskState(),
    var showDialog: Boolean = false,
)