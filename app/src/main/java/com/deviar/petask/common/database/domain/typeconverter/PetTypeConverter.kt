package com.deviar.petask.common.database.domain.typeconverter

import androidx.room.TypeConverter
import com.deviar.petask.common.database.data.model.PetType

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