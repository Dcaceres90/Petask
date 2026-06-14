package com.deviar.petask.auth.domain

import com.deviar.petask.auth.data.AuthRepository
import jakarta.inject.Inject

class SingOutUseCase @Inject constructor(
    private val repository: AuthRepository
)  {
    operator fun invoke() {
        repository.singOut()
    }
}