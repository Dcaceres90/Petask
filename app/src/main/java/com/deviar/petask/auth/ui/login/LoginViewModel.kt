package com.deviar.petask.auth.ui.login

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.deviar.petask.auth.data.AuthRepository
import com.deviar.petask.auth.data.GoogleAuthManager
import com.deviar.petask.auth.domain.LoginUseCase
import com.deviar.petask.auth.domain.ResetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val resetUseCase: ResetUseCase,
) : ViewModel() {
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
        loginUseCase(
            email = _uiState.value.email,
            password = _uiState.value.password,
        ) { error ->

            if (error == null) {
                _uiState.update { state ->
                    state.copy(loginSuccess = true)
                }
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
            resetUseCase(email = _uiState.value.email)
            { success ->
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

    suspend fun loginWithGoogle(context: Context) {

        val googleAuthManager = GoogleAuthManager(context)

        val success = googleAuthManager.signIn()

        if (success) {
            _uiState.update {
                it.copy(loginSuccess = true)
            }
        } else {
            _uiState.update {
                it.copy(firebaseError = "Google login failed")
            }
        }
    }
}

data class LoginUiState(
    val email:String = "",
    val password:String = "",
    val isLoginEnabled:Boolean = false,
    val firebaseError: String? = null,
    val recoveryMessage: String? = null,
    val loginSuccess: Boolean = false
)