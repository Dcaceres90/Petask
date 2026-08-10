package com.deviar.petask.pet.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deviar.petask.R
import com.deviar.petask.common.ui.theme.Primary
import com.deviar.petask.common.ui.theme.Secondary
import com.deviar.petask.pet.ui.PetViewModel
import com.deviar.petask.pet.domain.PetModel
import com.deviar.petask.pet.domain.PetState

@Composable
fun PetScreen(
    petViewModel: PetViewModel
) {

    val pet = petViewModel.pet
    val imageRes = when (pet.state) {
        PetState.HAPPY -> R.drawable.img_pet_orange_happy
        PetState.SAD -> R.drawable.img_pet_siamese_sad
        PetState.ANGRY -> R.drawable.img_pet_siamese_angry
        PetState.CONFUSED -> R.drawable.img_pet_siamese_confused

    }

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.img_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.weight(2f))

            Box(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            PetDetails(pet)
            Spacer(modifier = Modifier.weight(1f))


        }
    }
}

@Composable
fun PetDetails(pet: PetModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Secondary.copy(alpha = 0.6f)),
        border = BorderStroke(
            1.dp,
            Color.White.copy(alpha = 0.3f)
        )

    ) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = pet.name,
                modifier = Modifier.padding(vertical = 6.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic

            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Color.Black
                    ),
                    shape = MaterialTheme.shapes.medium
                ) { Text("Feed") }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier.weight(2f),
                    contentAlignment = Alignment.Center
                ) {
                    LinearProgressIndicator(
                        progress = { pet.hungerLevel / 6f },
                        color = Primary,
                        modifier = Modifier
                            .height(35.dp)
                            .fillMaxWidth(),
                        gapSize = (-30).dp,
                        drawStopIndicator = {}
                    )
                    Text("${((pet.hungerLevel / 6f) * 100).toInt()}%")
                }

            }

        }
    }
}