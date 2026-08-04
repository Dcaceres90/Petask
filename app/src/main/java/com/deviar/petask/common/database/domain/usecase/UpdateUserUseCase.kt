package com.deviar.petask.common.database.domain.usecase

import com.deviar.petask.common.database.data.UserRepository
import com.deviar.petask.common.database.domain.model.UserModel
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){

    suspend operator fun invoke(userName: String, imageUri: String?) {
        val userId = userRepository.getCurrentUserId() ?: return
        val user = UserModel(id = userId, userName = userName, imageUri = imageUri)
        userRepository.saveUser(user)
    }
}