package com.deviar.petask.pet

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.deviar.petask.auth.data.AuthRepository
import com.deviar.petask.auth.ui.register.RegisterViewModel

@Composable
fun PetScreen(
    petViewModel: PetViewModel,
    navigateToLogin: () -> Unit,
) {
    Column {
        Text("Bienvenido amiguito")
        Button(onClick = {
            petViewModel.singOut()
            navigateToLogin()
        }) { Text("Log Out") }
    }

}