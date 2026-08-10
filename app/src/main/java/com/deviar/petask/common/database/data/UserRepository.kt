package com.deviar.petask.common.database.data

import com.deviar.petask.common.database.domain.dao.UserDao
import com.deviar.petask.common.database.data.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val auth: FirebaseAuth
) {
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    suspend fun getUserById(userId: String): Flow<UserModel?> {
        return userDao.getUserById(userId)
    }

    suspend fun updateUserName(userName: String) {
        val userId = getCurrentUserId() ?: return
        userDao.updateUserName(userId, userName)
    }

    suspend fun updateProfileImage(imageUri: String?){
        val userId = getCurrentUserId() ?: return
        userDao.updateProfileImage(userId, imageUri)
    }

    suspend fun updateCoins(amount: Int) {
        val userId = getCurrentUserId() ?: return
        userDao.updateCoins(userId, amount)
    }
}