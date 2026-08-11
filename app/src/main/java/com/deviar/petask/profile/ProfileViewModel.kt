package com.deviar.petask.profile

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deviar.petask.common.database.domain.usecase.GetUserUseCase
import com.deviar.petask.common.database.domain.usecase.UpdateProfileImageUseCase
import com.deviar.petask.common.database.domain.usecase.UpdateUsernameUseCase
import com.deviar.petask.profile.domain.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUsernameUseCase: UpdateUsernameUseCase,
    private val updateProfileImageUseCase: UpdateProfileImageUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var state by mutableStateOf(ProfileState())

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            getUserUseCase().collect { user ->
                if (user != null) {
                    state = state.copy(
                        userName = user.userName,
                        imageUri = user.imageUri
                    )
                }
            }
        }
    }



    fun onChangeProfileImage(uri: Uri){
        viewModelScope.launch {
            val permanentUri = saveImageToInternalStorage(uri)

            if (permanentUri != null) {
                state = state.copy(
                    imageUri = permanentUri.toString()
                )
                updateProfileImageUseCase(permanentUri.toString())
            }
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): Uri? {
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

    fun onUserNameChange(userName: String){
        state = state.copy(
           userName = userName
        )
    }

    fun onUserNameEditDone() {
        viewModelScope.launch {
            updateUsernameUseCase(state.userName)
        }
    }


}