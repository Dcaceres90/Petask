package com.deviar.petask.pet.domain

import com.deviar.petask.common.database.data.model.PetType

data class PetUiState(
    val name: String = "",
    val type: PetType? = null,
    val hungerLevel: Int = 2,
    val petLevel: Int = 1,
    val state: PetState = PetState.HAPPY,
    val coins: Int = 0,
    val exp: Int = 0,
    val feedError: String? = null,
    val isLoading: Boolean = true
)

enum class PetState {
    HAPPY, SAD, ANGRY, CONFUSED
}
