package com.deviar.petask.pet

import androidx.lifecycle.ViewModel
import com.deviar.petask.auth.domain.SingOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val singOutUseCase: SingOutUseCase
) : ViewModel()  {
    fun singOut() {
        singOutUseCase()
    }
}