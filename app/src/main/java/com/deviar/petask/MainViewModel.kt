package com.deviar.petask

import androidx.lifecycle.ViewModel
import com.deviar.petask.auth.domain.SingOutUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val singOutUseCase: SingOutUseCase
): ViewModel() {

    val isLogged = Firebase.auth.currentUser != null

    fun singOut() {
        singOutUseCase()
    }
}