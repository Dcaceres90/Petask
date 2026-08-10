package com.deviar.petask.common.database.domain.usecase

import com.deviar.petask.common.database.data.UserRepository
import javax.inject.Inject

class UpdateUsernameUseCase @Inject constructor(
    private var userRepository: UserRepository
) {
    suspend operator fun invoke(userName: String){
        userRepository.updateUserName(userName)
    }
}