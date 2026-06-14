package com.deviar.petask.pet

import androidx.lifecycle.ViewModel
import com.deviar.petask.auth.domain.SingOutUseCase
import jakarta.inject.Inject

class PetViewModel @Inject constructor(
    private val singOutUseCase: SingOutUseCase
) : ViewModel()  {
    fun singOut() {
        singOutUseCase()
    }
}