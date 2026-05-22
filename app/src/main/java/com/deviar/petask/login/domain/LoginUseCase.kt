package com.deviar.petask.login.domain

import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(user: String, pass: String): Boolean {
        return repository.login(user, pass)
    }
}