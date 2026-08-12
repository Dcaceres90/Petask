package com.deviar.petask.common.database.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserModel (
    @PrimaryKey
    val id: String,
    val userName: String = "User",
    val imageUri: String? = null,
    val coins: Int = 0,
)

