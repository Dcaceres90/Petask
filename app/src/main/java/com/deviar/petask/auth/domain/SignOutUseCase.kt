package com.deviar.petask.auth.domain

import com.deviar.petask.auth.data.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository
)  {
    operator fun invoke() {
        repository.signOut()
    }
}