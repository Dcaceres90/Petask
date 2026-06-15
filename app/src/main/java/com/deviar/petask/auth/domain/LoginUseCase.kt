package com.deviar.petask.auth.domain

import com.deviar.petask.auth.data.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String, onResult: (String?) -> Unit) {
        repository.login(email, password, onResult)
    }
}