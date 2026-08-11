package com.deviar.petask.common.database.data

import com.deviar.petask.common.database.data.model.PetModel
import com.deviar.petask.common.database.domain.dao.PetDao
import com.google.firebase.auth.FirebaseAuth
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

    suspend fun getPet(): PetModel? {
        val userId = getCurrentUserId() ?: return null
        return petDao.getPetByUserId(userId)
    }

}