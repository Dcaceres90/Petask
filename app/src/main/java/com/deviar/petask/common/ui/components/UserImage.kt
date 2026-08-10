package com.deviar.petask.common.ui.components


import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.deviar.petask.R

@Composable
fun UserImage(
    imageUri: String?,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .clickable{ onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (!imageUri.isNullOrEmpty()) {
            // Intenta cargar la imagen desde la URI
            val uri = remember(imageUri) {
                try {
                    Uri.parse(imageUri)
                } catch (e: Exception) {
                    null
                }
            }

            if (uri != null) {
                AsyncImage(
                    model = uri,
                    contentDescription = "User profile image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Fallback si la URI no es válida
                DefaultUserImage()
            }
        } else {
            DefaultUserImage()
        }
    }
}

@Composable
private fun DefaultUserImage() {
    Image(
        painter = painterResource(R.drawable.ic_user_mage),
        contentDescription = "Default user image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

