package com.deviar.petask.createpet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deviar.petask.common.database.data.UserRepository
import com.deviar.petask.common.database.data.model.PetModel
import com.deviar.petask.common.database.data.model.PetType
import com.deviar.petask.common.database.domain.usecase.CreatePetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePetViewModel @Inject constructor(
    private val createPetUseCase: CreatePetUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreatePetUiState())
    val uiState: StateFlow<CreatePetUiState> = _uiState

    fun onPetNameChange(petName: String){
        _uiState.update { state ->
            state.copy(petName = petName)
        }
    }

    fun onPetSelected(pet : PetType){
        _uiState.update { state ->
            state.copy(selectedPet = pet)
        }
    }

    fun createPet(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val userId = userRepository.getCurrentUserId() ?: return@launch

            val selectedPet = _uiState.value.selectedPet ?: return@launch

            val pet = PetModel(
                userId = userId,
                type = selectedPet,
                petName = _uiState.value.petName
            )
            createPetUseCase(pet)
            onSuccess()
        }
    }


}