package com.deviar.petask.tasks.domain

import com.deviar.petask.common.utils.LevelDificult
import java.util.Date

data class TaskState (
    var idTask: Int = 0,
    var levelDificult: LevelDificult = LevelDificult.HARD,
    var dateToDoString: String = "23/02/1990",
    var dateToDo: Date = Date(),
    var textNewTask: String = "",
)