package com.deviar.petask.common.database.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deviar.petask.common.database.domain.dao.UserDao
import com.deviar.petask.common.database.data.model.UserModel

@Database(entities = [UserModel::class], version = 2)
abstract class PetaskDataBase: RoomDatabase() {
    abstract val userDao: UserDao
}