package com.deviar.petask.pet

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PetScreen(
    petViewModel: PetViewModel,
    navigateToLogin: () -> Unit,
) {
    Column {
        Text("Bienvenido amiguito")
        Button(onClick = {
            petViewModel.signOut()
            navigateToLogin()
        }) { Text("Log Out") }
    }

}