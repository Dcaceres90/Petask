package com.deviar.petask.pet.data

data class PetModel(
    val name: String,
    val hungerLevel: Int = 5,
    val state: PetState,
)

enum class PetState {
    HAPPY, SAD
}
