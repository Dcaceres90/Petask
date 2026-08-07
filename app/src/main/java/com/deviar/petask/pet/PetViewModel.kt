package com.deviar.petask.pet

import androidx.lifecycle.ViewModel
import com.deviar.petask.auth.domain.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
): ViewModel() {
    fun signOut() {
        signOutUseCase()
    }
}