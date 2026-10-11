package com.deviar.petask.onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.common.database.data.model.PetType
import com.deviar.petask.common.ui.components.textfields.PetaskTextField
import com.deviar.petask.R
import com.deviar.petask.common.ui.components.PetaskButton

@Composable
fun OnboardingScreen(
    onboardingViewModel: OnboardingViewModel,
    navigateToPet: () -> Unit
) {

    val uiState by onboardingViewModel.uiState.collectAsStateWithLifecycle()

    fun getPetImage(petType: PetType): Int {
        return when (petType) {
            PetType.ORANGE_CAT -> R.drawable.img_pet_orange_happy
            PetType.SIAMESE_CAT -> R.drawable.img_pet_siamese_happy
            PetType.GRAY_CAT -> R.drawable.img_pet_gray_happy
        }
    }

    val pets = PetType.entries


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "What is your name?",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        PetaskTextField(
            value = uiState.name,
            onValueChange = {
                onboardingViewModel.onNameChange(it)
            },
            label = "Name"
        )
        Spacer(modifier = Modifier.height(32.dp))


        Text(
            text = "How do you want to call your kitten?",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        PetaskTextField(
            value = uiState.petName,
            onValueChange = { petName ->
                onboardingViewModel.onPetNameChange(petName)
            },
            label = "Pet's name"
        )
        Spacer(modifier = Modifier.height(32.dp))


        Text(
            text = "Pick your kitten color",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            pets.forEach { pet ->

                Image(
                    painterResource(getPetImage(pet)),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clickable{ onboardingViewModel.onPetSelected(pet) }
                )
            }

        }

        PetaskButton(
            onClick = {
                onboardingViewModel.createUser(
                    onSuccess = {navigateToPet()}
                )
            },
            text = "Start your Journey",
            enabled = uiState.name.isNotBlank()
                    && uiState.petName.isNotBlank()
                    && uiState.selectedPet != null
        )

        Spacer(modifier = Modifier.height(50.dp))

        PetCareDisclaimer()

    }
}


