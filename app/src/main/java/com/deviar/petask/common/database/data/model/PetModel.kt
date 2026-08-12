package com.deviar.petask.common.database.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

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

class PetTypeConverter {

    @TypeConverter
    fun fromPetType(type: PetType): String {
        return type.name
    }

    @TypeConverter
    fun toPetType(type: String): PetType {
        return PetType.valueOf(type)
    }
}