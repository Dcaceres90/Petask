package com.deviar.petask.common.database.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deviar.petask.common.database.data.model.PetModel

@Dao
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetModel)

    @Query("SELECT * FROM PetModel WHERE userId = :userId")
    suspend fun getPetByUserId(userId: String): PetModel?
}