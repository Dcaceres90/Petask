package com.deviar.petask.common.database.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deviar.petask.common.database.data.model.PetModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetModel)

    @Query("SELECT * FROM PetModel " + "WHERE userId = :userId")
    fun getPetByUserId(userId: String): Flow<PetModel?>

    @Query("""UPDATE PetModel
    SET hunger = hunger + 1
    WHERE userId = :userId
""")
    suspend fun increaseHunger (userId : String)

    @Query("""
    UPDATE PetModel
    SET hunger = :hunger,
        lastHungerUpdate = :lastHungerUpdate
    WHERE userId = :userId
""")
    suspend fun decreaseHunger(
        userId: String,
        hunger: Int,
        lastHungerUpdate: Long
    )
}