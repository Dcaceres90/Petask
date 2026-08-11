package com.deviar.petask.onboarding.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deviar.petask.common.database.data.UserRepository
import com.deviar.petask.common.database.data.model.PetModel
import com.deviar.petask.common.database.data.model.PetType
import com.deviar.petask.common.database.data.model.UserModel
import com.deviar.petask.common.database.domain.usecase.CreatePetUseCase
import com.deviar.petask.common.database.domain.usecase.CreateUserUseCase
import com.deviar.petask.onboarding.domain.OnboardingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val createPetUseCase: CreatePetUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState

    fun onNameChange(name: String) {
        _uiState.update { state ->
            state.copy(name = name)
        }
    }

    fun onPetNameChange(petName: String) {
            _uiState.update { state ->
                state.copy(petName = petName)
            }
        }

    fun onPetSelected(pet: PetType) {
            _uiState.update { state ->
                state.copy(selectedPet = pet)
            }
        }

    fun createUser(
            onSuccess: () -> Unit
        ) {
            viewModelScope.launch {

                val userId = userRepository.getCurrentUserId()
                    ?: return@launch

                val selectedPet = _uiState.value.selectedPet
                    ?: return@launch

                val user = UserModel(
                    id = userId,
                    userName = _uiState.value.name
                )

                val pet = PetModel(
                    userId = userId,
                    type = selectedPet,
                    petName = _uiState.value.petName
                )

                createUserUseCase(user)
                createPetUseCase(pet)

                onSuccess()
            }
        }
    }
