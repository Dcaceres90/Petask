package com.deviar.petask.common.database.domain.usecase

import com.deviar.petask.common.database.data.UserRepository
import com.deviar.petask.common.database.domain.model.UserModel
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): UserModel?{
        val userId = userRepository.getCurrentUserId() ?: return null
        return userRepository.getUserById(userId)
    }
}