package com.deviar.petask.common.database.domain.usecase

import com.deviar.petask.common.database.data.PetRepository
import javax.inject.Inject

class DeletePetUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(){
        petRepository.deletePet()
    }
}