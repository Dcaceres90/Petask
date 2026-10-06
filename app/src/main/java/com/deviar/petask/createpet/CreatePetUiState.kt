package com.deviar.petask.createpet

import com.deviar.petask.common.database.data.model.PetType

data class CreatePetUiState (
    val petName: String = "",
    val selectedPet: PetType? = null
)