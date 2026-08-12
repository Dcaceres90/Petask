package com.deviar.petask.common.database.domain.usecase

import com.deviar.petask.common.database.data.model.PetModel
import com.deviar.petask.common.database.data.PetRepository
import javax.inject.Inject

class CreatePetUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(pet : PetModel){
        petRepository.savePet(pet)
    }
}