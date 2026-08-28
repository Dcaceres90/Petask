package com.deviar.petask.tasks.domain

import com.deviar.petask.common.utils.LevelDificult
import java.util.Date

data class NewTaskFormState (
    var title: String = "Título de la tarea",
    var levelDificult: LevelDificult = LevelDificult.HARD,
    var showDialog: Boolean = false,
    var dateToDoString: String = "23/02/1990",
    var textNewTask: String = "",
    var dateToDo: Date = Date(),
)