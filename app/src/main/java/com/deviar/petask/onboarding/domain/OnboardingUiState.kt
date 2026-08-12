package com.deviar.petask.onboarding.domain

import com.deviar.petask.common.database.data.model.PetType

data class OnboardingUiState (
    val name: String = "",
    val petName: String = "",
    val selectedPet: PetType? = null
)