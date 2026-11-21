package com.deviar.petask.createpet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.R
import com.deviar.petask.common.database.data.model.PetType
import com.deviar.petask.common.ui.components.PetaskButton
import com.deviar.petask.common.ui.components.textfields.PetaskTextField
import com.deviar.petask.common.ui.theme.Primary
import com.deviar.petask.common.ui.theme.Secondary
import com.deviar.petask.onboarding.ui.PetCareDisclaimer

@Composable
fun CreatePetScreen(
    modifier: Modifier,
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
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 20.dp)
    ) {

        Text(
            text = "OH NO! Your pet decided to move in with the neighbor next door. They offered snacks way more often! But don’t worry, you can always welcome a new little friend!",
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Choose your new kitten",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            pets.forEach { pet ->
                val isSelected = uiState.selectedPet == pet

                Image(
                    painter = painterResource(getPetImage(pet)),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(
                            if (isSelected) Secondary.copy(alpha = 0.2f)
                            else Color.Transparent
                        )
                        .border(
                            width = if (isSelected) 2.dp else 0.dp,
                            color = if (isSelected) Primary else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            createPetViewModel.onPetSelected(pet)
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "How do you want to call your kitten?",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        PetaskTextField(
            value = uiState.petName,
            onValueChange = {
                createPetViewModel.onPetNameChange(it)
            },
            label = "Pet's Name"
        )

        Spacer(modifier = Modifier.height(12.dp))


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

        Spacer(modifier = Modifier.height(16.dp))

        PetCareDisclaimer()
    }
}