package com.deviar.petask.pet.ui

import androidx.lifecycle.ViewModel
import com.deviar.petask.pet.domain.PetModel
import com.deviar.petask.pet.domain.PetState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor() : ViewModel()  {

    val pet = PetModel(
        name = "Luna",
        hungerLevel = 1,
        state = PetState.HAPPY
    )
}