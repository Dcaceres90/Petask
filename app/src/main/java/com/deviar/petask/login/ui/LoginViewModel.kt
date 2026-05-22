package com.deviar.petask.login.ui

import androidx.lifecycle.ViewModel
import com.deviar.petask.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
}