package com.deviar.petask.tasks.domain

import com.deviar.petask.common.utils.LevelDificult

data class NewTaskFormState (
    var title: String = "Título de la tarea",
    var levelDificult: LevelDificult = LevelDificult.EASY,
    var dateToDo: String = "23/02/1990",
)