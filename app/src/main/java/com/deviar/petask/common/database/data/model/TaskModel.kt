package com.deviar.petask.common.database.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deviar.petask.common.utils.LevelDificult
import java.util.Date

@Entity
data class TaskModel(
    // El enum de dificultad guardarlo en la task (exp, coin, Nombre)
    @PrimaryKey(autoGenerate = true)
    val idTask: Int = 0,
    val idUser: String = "",
    val text: String = "",
    val isComplete: Boolean = false,
    val levelDificult: LevelDificult = LevelDificult.EASY,
    val toDoDate: Date = Date(),
)