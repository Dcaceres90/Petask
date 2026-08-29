package com.deviar.petask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deviar.petask.auth.domain.SingOutUseCase
import com.deviar.petask.common.database.domain.usecase.GetPetUseCase
import com.deviar.petask.common.database.domain.usecase.UpdateCoinsUseCase
import com.deviar.petask.common.database.domain.usecase.GetUserUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val singOutUseCase: SingOutUseCase,
    private val updateCoinsUseCase: UpdateCoinsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getPetUseCase: GetPetUseCase
): ViewModel() {

    val isLogged = Firebase.auth.currentUser != null


    var state by mutableStateOf(MainState())

    init {
        observeUser()
    }

    private fun observeUser() {

        viewModelScope.launch {

            combine(
                getUserUseCase(),
                getPetUseCase()
            ) { user, pet ->

                MainState(
                    coins = user?.coins ?: 0,
                    hasUserModel = user != null,
                    hasPetModel = pet != null,
                    isLoading = false
                )
            }.collect { newState ->
                state = newState
            }
        }
    }

    fun earnCoins(amount: Int) {
        viewModelScope.launch {
            updateCoinsUseCase(amount)
        }
    }

    fun spendCoins(amount: Int) {
        viewModelScope.launch {
            updateCoinsUseCase(-amount)
        }
    }

    fun singOut() {
        singOutUseCase()
    }
}

data class MainState(
    val coins: Int = 0,
    val hasUserModel: Boolean = false,
    val hasPetModel: Boolean = false,
    val isLoading: Boolean = true
)