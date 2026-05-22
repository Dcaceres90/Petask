package com.deviar.petask.login.data

import com.deviar.petask.login.domain.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor() : LoginRepository {
    override suspend fun login(user: String, pass: String): Boolean {
        // Implementation here
        return true
    }
}