package com.deviar.petask.common.database.domain.usecase

import com.deviar.petask.common.database.data.UserRepository
import com.deviar.petask.common.database.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<UserModel?> {
        val userId = userRepository.getCurrentUserId()

        return if (userId != null) {
            userRepository.getUserById(userId)
        } else {
            flowOf(null)
        }
    }
}