package com.deviar.petask.pet

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.deviar.petask.auth.data.AuthRepository

@Composable
fun PetScreen(
    navigateToLogin: () -> Unit
) {
    val repository = AuthRepository()
    Column {
        Text("Bienvenido amiguito")
        Button(onClick = {
            repository.singOut()
            navigateToLogin()
        }) { Text("Log Out") }
    }

}