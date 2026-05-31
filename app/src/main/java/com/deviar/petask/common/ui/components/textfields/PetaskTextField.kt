package com.deviar.petask.common.ui.components.textfields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import com.deviar.petask.common.ui.theme.Primary

@Composable
fun PetaskTextField(value:String, onValueChange: (String) -> Unit, label:String, error: String? = null, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.padding(bottom = 16.dp).fillMaxWidth(),
        label = {Text(label)},
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary
        ),
        isError = error != null
    )
    if(error != null){
        Text(
            text = error,
            color = Color.Red
        )
    }
}

