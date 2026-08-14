package com.deviar.petask.common.database.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deviar.petask.common.database.data.model.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserModel)

    @Query("SELECT * FROM UserModel WHERE id = :userId")
    fun getUserById(userId: String): Flow<UserModel?>

    @Query("UPDATE UserModel SET userName = :userName WHERE id = :userId")
    suspend fun updateUserName(userId: String, userName: String)

    @Query("UPDATE UserModel SET imageUri = :imageUri WHERE id = :userId")
    suspend fun updateProfileImage(userId: String, imageUri: String?)

    @Query("UPDATE UserModel SET coins = coins + :amount WHERE id = :userId ")
    suspend fun updateCoins(userId: String, amount: Int)

}