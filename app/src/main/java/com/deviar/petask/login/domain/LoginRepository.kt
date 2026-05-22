package com.deviar.petask.login.domain

interface LoginRepository {
    suspend fun login(user: String, pass: String): Boolean
}