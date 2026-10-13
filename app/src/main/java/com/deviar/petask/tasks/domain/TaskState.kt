package com.deviar.petask.tasks.domain

import com.deviar.petask.common.utils.LevelDificult
import java.util.Date

data class TaskState (
    var idTask: Int = 0,
    var levelDificult: LevelDificult = LevelDificult.EASY,
    var dateToDoString: String = "02/23",
    var dateToDo: Date = Date(),
    var textNewTask: String = "",
    var isComplete: Boolean = false,
)