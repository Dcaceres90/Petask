package com.deviar.petask.common.database.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deviar.petask.common.database.domain.model.UserModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserModel)

    @Query("SELECT * FROM UserModel WHERE id = :userId")
    suspend fun getUserById(userId: String): UserModel?

}