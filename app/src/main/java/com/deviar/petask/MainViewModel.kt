package com.deviar.petask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deviar.petask.auth.domain.SignOutUseCase
import com.deviar.petask.common.database.domain.usecase.UpdateCoinsUseCase
import com.deviar.petask.common.database.domain.usecase.GetUserUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val singOutUseCase: SignOutUseCase,
    private val updateCoinsUseCase: UpdateCoinsUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    val isLogged = Firebase.auth.currentUser != null

    var state by mutableStateOf(MainState())

    init {
        observeUser()
    }

    private fun observeUser() {

        viewModelScope.launch {

            getUserUseCase().collect { user ->

                state = state.copy(
                    coins = user?.coins ?: 0
                )
            }
        }
    }

    /*private fun loadCoins() {
        viewModelScope.launch {
            val user = getUserUseCase() ?: return@launch

            state = state.copy(
                coins = user.coins
            )
        }
    }*/

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
    val coins: Int = 0
)