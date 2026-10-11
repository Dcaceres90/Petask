package com.deviar.petask.common.database.domain.usecase

import com.deviar.petask.common.database.data.PetRepository
import javax.inject.Inject

class UpdateExpUseCase @Inject constructor(
    private var petRepository: PetRepository
) {
    suspend operator fun invoke(amount : Int){
        petRepository.updateExp(amount)
    }
}