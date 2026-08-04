package com.deviar.petask.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deviar.petask.common.database.domain.usecase.GetUserUseCase
import com.deviar.petask.common.database.domain.usecase.UpdateUserUseCase
import com.deviar.petask.profile.domain.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var state by mutableStateOf(UserState())

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val user = getUserUseCase()
            if (user != null) {
                state = state.copy(
                    userName = user.userName,
                    imageUri = user.imageUri
                )
            }
        }
    }

    private suspend fun saveImageToInternalStorage(uri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val fileName = "profile_${System.currentTimeMillis()}.jpg"  // nombre único
            val file = File(context.filesDir, fileName)
            file.outputStream().use { output ->
                inputStream.copyTo(output)
            }
            Uri.fromFile(file)   // devuelve un file:// URI
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun onChangeProfileImage(uri: Uri){
        viewModelScope.launch {
            val permanentUri = saveImageToInternalStorage(uri)
            if (permanentUri != null) {
                state = state.copy(imageUri = permanentUri.toString())
                saveUser()
            }
        }
    }

    fun onUserNameChange(userName: String){
        state = state.copy(
           userName = userName
        )
    }

    fun onUserNameEditDone() {
        saveUser()
    }

    private fun saveUser() {
        viewModelScope.launch {
            updateUserUseCase(
                userName = state.userName,
                imageUri = state.imageUri
            )
        }
    }

}