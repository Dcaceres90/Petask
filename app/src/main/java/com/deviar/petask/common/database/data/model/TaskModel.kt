package com.deviar.petask.common.database.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class TaskModel(
    // El enum de dificultad guardarlo en la task (exp, coin, Nombre)
    @PrimaryKey
    val idTask: String,
    val idUser: String,
    val text: String,
    val isComplete: Boolean,
    val exp: Int,
    val coinValue: Int,
    val toDoDate: Date,
)