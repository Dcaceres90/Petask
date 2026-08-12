package com.deviar.petask.common.database.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deviar.petask.common.database.data.model.PetModel
import com.deviar.petask.common.database.data.model.PetTypeConverter
import com.deviar.petask.common.database.domain.dao.UserDao
import com.deviar.petask.common.database.data.model.UserModel
import com.deviar.petask.common.database.domain.dao.PetDao

@Database(entities = [UserModel::class, PetModel::class], version = 3)

@TypeConverters(PetTypeConverter::class)
abstract class PetaskDataBase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val petDao: PetDao
}