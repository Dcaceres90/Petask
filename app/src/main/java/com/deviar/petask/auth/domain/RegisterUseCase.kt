package com.deviar.petask.auth.domain

import com.deviar.petask.auth.data.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
)  {
    operator fun invoke(
        email: String,
        password: String,
        onResult: (String?) -> Unit,
    ) {
        repository.register(email, password, onResult)
    }
}