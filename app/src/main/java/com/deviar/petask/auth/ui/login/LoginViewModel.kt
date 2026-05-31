package com.deviar.petask.auth.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.deviar.petask.auth.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {

    private val repository = AuthRepository()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChanged(email: String){
        _uiState.update { state ->
            state.copy(email = email, firebaseError = null)
        }
        verifyLogin()
    }

    fun onPasswordChanged(password: String){
        _uiState.update { state ->
            state.copy(password = password, firebaseError = null)
        }
        verifyLogin()
    }

    fun verifyLogin(){
        val enabledLogin:Boolean = isEmailFormatValid(_uiState.value.email) && isPasswordFormatValid(_uiState.value.password)
        _uiState.update { state ->
            state.copy(isLoginEnabled = enabledLogin)
        }
    }

    fun isEmailFormatValid(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isPasswordFormatValid(password: String):Boolean = password.length >= 6

    fun login() {
        repository.login(
            email = _uiState.value.email,
            password = _uiState.value.password
        ) { error ->

            if (error == null) {
                Log.i("Iara", "Login successful")
            } else {
                _uiState.update { state ->
                    state.copy(firebaseError = error)
                }
            }
        }
    }

    fun resetPassword(){

        if (!isEmailFormatValid(_uiState.value.email)) {
            _uiState.update {
                it.copy(recoveryMessage = "Please enter a valid email")
            }
        }else{
            repository.resetPassword(
                email = _uiState.value.email
            ){success ->
                _uiState.update { state ->
                    state.copy(
                        recoveryMessage =
                            if (success)
                                "Recovery email sent successfully"
                            else
                                "Failed to send recovery email"
                    )
                }

            }
        }

    }
}

data class LoginUiState(
    val email:String = "",
    val password:String = "",
    val isLoginEnabled:Boolean = false,
    val firebaseError: String? = null,
    val recoveryMessage: String? = null
)