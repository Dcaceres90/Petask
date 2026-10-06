package com.deviar.petask.pet.domain.usecase

import com.deviar.petask.common.database.data.PetRepository
import javax.inject.Inject

class FeedPetUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(){
        petRepository.increaseHunger()
    }
}