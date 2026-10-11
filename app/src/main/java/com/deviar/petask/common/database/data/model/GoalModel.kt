package com.deviar.petask.common.database.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GoalModel(
    @PrimaryKey
    val goalId: String,
    val userId: String,
    val text: String,
    val isComplete: Boolean,
    val goalType: GoalType
)

enum class GoalType (
    val exp: Int,
    val coinValue: Int) {
    WEEKLY (exp = 100, coinValue = 200),
    MONTHLY (exp = 200, coinValue = 400)
}