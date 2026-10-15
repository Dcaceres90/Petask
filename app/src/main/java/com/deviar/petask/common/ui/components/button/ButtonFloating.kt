package com.deviar.petask.common.ui.components.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ButtonFloating(
    onClickFloating: () -> Unit,
    modifier: Modifier = Modifier,
    descricionButton: String = "Agregar",
    icSimboloFav: ImageVector = Icons.Default.Add,
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClickFloating,
    ) {
        Icon(
            imageVector = icSimboloFav,
            contentDescription = descricionButton,
            tint = Color.White,
        )
    }
}


