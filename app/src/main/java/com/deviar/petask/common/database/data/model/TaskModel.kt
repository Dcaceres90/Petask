package com.deviar.petask.common.database.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class TaskModel(
    @PrimaryKey
    val idTask: String,
    val idUser: String,
    val text: String,
    val isComplete: Boolean,
    val toDoDate: Date,
)