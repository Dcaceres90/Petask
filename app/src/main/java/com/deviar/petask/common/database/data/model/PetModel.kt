package com.deviar.petask.common.database.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PetModel (
    @PrimaryKey
    val userId: String,
    val type: PetType,
    val petName: String,
    val hunger: Int = 5,
    val level: Int = 1
)

enum class PetType {
    ORANGE_CAT,
    SIAMESE_CAT,
    GRAY_CAT
}