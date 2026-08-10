package com.deviar.petask.common.database.domain.usecase

import com.deviar.petask.common.database.data.UserRepository
import javax.inject.Inject

class UpdateCoinsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(amount: Int){
        userRepository.updateCoins(amount)
    }
}