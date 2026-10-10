package com.deviar.petask.common.database.domain.usecase

import com.deviar.petask.common.database.data.UserRepository
import com.deviar.petask.common.database.data.model.UserModel
import javax.inject.Inject

class GetUIDUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): String? {
        return userRepository.getCurrentUserId()
    }

}