package com.deviar.petask.common.database.data

import com.deviar.petask.common.database.data.model.PetModel
import com.deviar.petask.common.database.domain.dao.PetDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PetRepository @Inject constructor(
    private val petDao: PetDao,
    private val auth: FirebaseAuth
) {

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    suspend fun savePet(pet: PetModel) {
        petDao.insertPet(pet)
    }

    suspend fun getPet(): Flow<PetModel?> {
        val userId = getCurrentUserId()
        return if (userId != null) {
            petDao.getPetByUserId(userId)
        } else {
            flowOf(null)
        }
    }

    suspend fun increaseHunger() {
        val userId = getCurrentUserId() ?: return
        return petDao.increaseHunger(userId)
    }

    suspend fun updateHunger(hunger: Int, lastHungerUpdate: Long) {
        val userId = getCurrentUserId() ?: return

        petDao.decreaseHunger(
            userId = userId,
            hunger = hunger,
            lastHungerUpdate = lastHungerUpdate
        )
    }

    suspend fun updateExp(amount: Int){
        val userId = getCurrentUserId() ?: return
        petDao.updateExp(userId, amount)
    }

    suspend fun deletePet() {
        val userId = getCurrentUserId() ?: return
        petDao.deletePet(userId)
    }

}