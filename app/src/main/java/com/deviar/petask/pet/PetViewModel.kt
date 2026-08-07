package com.deviar.petask.pet

import androidx.lifecycle.ViewModel
import com.deviar.petask.pet.data.PetModel
import com.deviar.petask.pet.data.PetState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor() : ViewModel()  {

    val pet = PetModel(
        name = "Luna",
        hungerLevel = 3,
        state = PetState.HAPPY
    )
}