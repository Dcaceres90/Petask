package com.deviar.petask.goals.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

val LocalNestedDialogState = compositionLocalOf { NestedDialogState() }

class NestedDialogState {
    var isOpen by mutableStateOf(false)
}