package com.deviar.petask.pet

import androidx.lifecycle.ViewModel
import com.deviar.petask.auth.domain.SingOutUseCase
import com.deviar.petask.pet.data.PetModel
import com.deviar.petask.pet.data.PetState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val singOutUseCase: SingOutUseCase
) : ViewModel()  {

    val pet = PetModel(
        name = "Fluffy",
        hungerLevel = 3,
        state = PetState.HAPPY
    )


    fun singOut() {
        singOutUseCase()
    }

}