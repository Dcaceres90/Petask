package com.deviar.petask.common.database.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deviar.petask.common.database.data.model.GoalModel
import com.deviar.petask.common.database.data.model.PetModel

import com.deviar.petask.common.database.domain.dao.UserDao
import com.deviar.petask.common.database.data.model.UserModel
import com.deviar.petask.common.database.domain.dao.GoalDao
import com.deviar.petask.common.database.domain.dao.PetDao
import com.deviar.petask.common.database.domain.typeconverter.PetTypeConverter

@Database(entities = [UserModel::class, PetModel::class, GoalModel::class], version = 6)

@TypeConverters(PetTypeConverter::class)
abstract class PetaskDataBase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val petDao: PetDao
    abstract val goalDao : GoalDao
}