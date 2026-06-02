package com.deviar.petask.auth.ui.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.deviar.petask.auth.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.Boolean

class RegisterViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState


    fun onEmailChanged(email: String) {

        val emailError =
            if (isEmailFormatValid(email)) null
            else "Invalid email format"

        _uiState.update { state ->
            state.copy(email = email, emailError = emailError, firebaseError = null)
        }
        verifyRegister()
    }

    fun onPasswordChanged(password: String) {

        val passwordError =
            if (isPasswordFormatValid(password)) null
            else "Password must contain at least 6 characters"

        _uiState.update { state ->
            state.copy(password = password, passwordError = passwordError, firebaseError = null)
        }
        verifyRegister()
    }

    fun onConfirmPasswordChanged(repeatedPassword: String) {

        val confirmPasswordError =
            if (_uiState.value.password == repeatedPassword) null
            else "Passwords do not match"

        _uiState.update { state ->
            state.copy(
                confirmedPassword = repeatedPassword,
                confirmPasswordError = confirmPasswordError,
                firebaseError = null
            )
        }
        verifyRegister()
    }

    fun onTermsAccepted(accepted: Boolean) {
        _uiState.update { state ->
            state.copy(termsAndConditions = accepted)
        }
        verifyRegister()
    }


    fun verifyRegister() {
        val state = _uiState.value
        val enabledRegister: Boolean =
                    state.emailError == null &&
                    state.passwordError == null &&
                    state.confirmPasswordError == null &&
                    state.email.isNotBlank() &&
                    state.password.isNotBlank() &&
                    state.confirmedPassword.isNotBlank() &&
                    state.termsAndConditions
        _uiState.update { state ->
            state.copy(isRegisterEnabled = enabledRegister)
        }
    }


    fun isEmailFormatValid(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isPasswordFormatValid(password: String): Boolean = password.length >= 6

    fun register(){
        repository.register(
            email = _uiState.value.email,
            password = _uiState.value.password
        ){error ->
            if(error == null){
                _uiState.update { state ->
                    state.copy(registerSuccess = true)
                }
            } else {
                _uiState.update { state ->
                    state.copy(firebaseError = error)
                }
            }
        }
    }

}

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmedPassword: String = "",
    val termsAndConditions: Boolean = false,
    val isRegisterEnabled: Boolean = false,

    //Show error
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val firebaseError: String? = null,

    val registerSuccess: Boolean = false
)