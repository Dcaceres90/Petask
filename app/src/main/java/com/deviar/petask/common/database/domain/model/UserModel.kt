package com.deviar.petask.common.database.domain.model

data class UserModel (
    val id: String = "",
    val name: String = "User",
    val imageUri: String? = null,
    val coins: Int = 0,
    val selectedPet: PetModel
)

