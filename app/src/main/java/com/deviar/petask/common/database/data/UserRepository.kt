package com.deviar.petask.common.database.data

import com.deviar.petask.common.database.domain.dao.UserDao
import com.deviar.petask.common.database.domain.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val auth: FirebaseAuth
) {
    fun getCurrentUserId(): String? = auth.currentUser?.uid

    suspend fun getUserById(userId: String): UserModel? {
        return userDao.getUserById(userId)
    }

    suspend fun saveUser(user: UserModel) {
        userDao.insertUser(user)
    }
}