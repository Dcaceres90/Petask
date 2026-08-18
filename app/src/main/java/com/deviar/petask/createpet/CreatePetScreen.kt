package com.deviar.petask.createpet

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.R
import com.deviar.petask.common.database.data.model.PetType
import com.deviar.petask.common.ui.components.PetaskButton
import com.deviar.petask.common.ui.components.textfields.PetaskTextField

@Composable
fun CreatePetScreen(
    createPetViewModel: CreatePetViewModel,
    navigateToPet: () -> Unit
) {

    val uiState by createPetViewModel.uiState.collectAsStateWithLifecycle()

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
    ) {

        Spacer(modifier = Modifier.height(200.dp))

        Text("Choose your new kitten")

        Text("How do you want to call your kitten?")

        PetaskTextField(
            value = uiState.petName,
            onValueChange = {
                createPetViewModel.onPetNameChange(it)
            },
            label = "petName"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            pets.forEach { pet ->

                Image(
                    painter = painterResource(getPetImage(pet)),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clickable {
                            createPetViewModel.onPetSelected(pet)
                        }
                )
            }
        }

        PetaskButton(
            onClick = {
                createPetViewModel.createPet(
                    onSuccess = {
                        navigateToPet()
                    }
                )
            },
            text = "Start your Journey",
            enabled = uiState.petName.isNotBlank()
                    && uiState.selectedPet != null
        )
    }
}