package com.deviar.petask.common.database.domain.usecase

import com.deviar.petask.common.database.data.model.PetModel
import com.deviar.petask.common.database.data.PetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPetUseCase@Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(): Flow<PetModel?> {
        return petRepository.getPet()
    }
}