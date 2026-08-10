package com.deviar.petask.pet.domain

data class PetModel(
    val name: String,
    val hungerLevel: Int = 2,
    val state: PetState,
)

enum class PetState {
    HAPPY, SAD, ANGRY, CONFUSED
}
