package com.deviar.petask.auth.domain

import com.deviar.petask.auth.data.AuthRepository
import javax.inject.Inject

class ResetUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, onResult: (Boolean) -> Unit) {
        repository.resetPassword(email, onResult)
    }
}