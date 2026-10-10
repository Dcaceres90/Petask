package com.deviar.petask.common.database.domain.typeconverter

import androidx.room.TypeConverter
import com.deviar.petask.common.database.data.model.PetType
import java.util.Date

class PetaskTypeConverter {
    //Fecha
    @TypeConverter
    fun toDate(value: Long?): Date? = if (value == null) null else Date(value)

    @TypeConverter
    fun toLong(value: Date?): Long? = value?.time

    //Enum
    @TypeConverter
    fun fromPetType(type: PetType): String {
        return type.name
    }

    @TypeConverter
    fun toPetType(type: String): PetType {
        return PetType.valueOf(type)
    }
}