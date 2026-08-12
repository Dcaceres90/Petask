package com.deviar.petask.common.database.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deviar.petask.common.database.data.model.PetModel
import com.deviar.petask.common.database.data.model.TaskModel
import com.deviar.petask.common.database.domain.dao.UserDao
import com.deviar.petask.common.database.data.model.UserModel
import com.deviar.petask.common.database.domain.dao.PetDao
import com.deviar.petask.common.database.domain.dao.TaskDao
import com.deviar.petask.common.database.domain.typeconverter.PetaskTypeConverter

@Database(entities = [UserModel::class, PetModel::class, TaskModel::class], version = 4)

@TypeConverters(PetaskTypeConverter::class)
abstract class PetaskDataBase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val petDao: PetDao
    abstract val taskDao: TaskDao
}