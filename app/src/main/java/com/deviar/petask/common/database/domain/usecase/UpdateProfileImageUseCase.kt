package com.deviar.petask.common.database.domain.usecase

import android.net.Uri
import com.deviar.petask.common.database.data.UserRepository
import javax.inject.Inject

class UpdateProfileImageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(imageUri: String?){
        userRepository.updateProfileImage(imageUri)
    }
}