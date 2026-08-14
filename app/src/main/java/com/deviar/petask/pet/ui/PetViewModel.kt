package com.deviar.petask.pet.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deviar.petask.common.database.domain.usecase.GetPetUseCase
import com.deviar.petask.common.database.domain.usecase.GetUserUseCase
import com.deviar.petask.common.database.domain.usecase.UpdateCoinsUseCase
import com.deviar.petask.pet.domain.PetState
import com.deviar.petask.pet.domain.PetUiState
import com.deviar.petask.pet.domain.usecase.DecreaseHungerUseCase
import com.deviar.petask.pet.domain.usecase.FeedPetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val getPetUseCase: GetPetUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateCoinsUseCase: UpdateCoinsUseCase,
    private val feedPetUseCase: FeedPetUseCase,
    private val decreaseHungerUseCase: DecreaseHungerUseCase
) : ViewModel()  {

    val _uiState = MutableStateFlow(PetUiState())
    val uiState: StateFlow<PetUiState> = _uiState

    init {
        observePet()
    }

    fun observePet(){

        viewModelScope.launch {
            getPetUseCase().collect { pet ->

                if (pet == null) return@collect

                val user = getUserUseCase().first()

                _uiState.update { state ->
                    state.copy(
                        name = pet.petName,
                        type = pet.type,
                        hungerLevel = pet.hunger,
                        petLevel = pet.level,
                        state = calculatePetState(pet.hunger),
                        coins = user?.coins ?: 0,
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun refreshPet(){
        viewModelScope.launch {
            val pet = getPetUseCase().first()
                ?: return@launch

            decreaseHungerUseCase(pet)
        }
    }

    private fun calculatePetState(
        hunger: Int
    ): PetState {

        return when {
            hunger >= 5 -> PetState.HAPPY
            hunger >= 1 -> PetState.SAD
            else -> PetState.ANGRY
        }
    }

    fun feedPet(){

        viewModelScope.launch {
            val pet = getPetUseCase().first() ?: return@launch
            val user = getUserUseCase().first() ?: return@launch

            if (pet.hunger >= 6) {
                _uiState.update {
                    it.copy(feedError = "I'm full")
                }
                return@launch
            }

            if (user.coins < 100) {
                _uiState.update {
                    it.copy(feedError = "Complete more tasks to feed me")
                }
                return@launch
            }

            feedPetUseCase()
            updateCoinsUseCase(-100)

            _uiState.update {
                it.copy(feedError = null)
            }

        }

    }


}