package com.deviar.petask.common.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.deviar.petask.common.ui.theme.Secondary
import com.deviar.petask.goals.LocalNestedDialogState
import com.deviar.petask.goals.NestedDialogState


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddDialog(
    onDismiss: () -> Unit,
    onContent: @Composable () -> Unit,
) {

    val keyboardVisible = WindowInsets.isImeVisible
    var isFirstTimeOpened by remember { mutableStateOf(true) }
    val nestedDialogState = remember { NestedDialogState() }

    LaunchedEffect(keyboardVisible) {
        if (!isFirstTimeOpened && !keyboardVisible && !nestedDialogState.isOpen) {
            onDismiss()
        }
        isFirstTimeOpened = false
    }

    Dialog(
        onDismissRequest = onDismiss,
        content = {
            CompositionLocalProvider(LocalNestedDialogState provides nestedDialogState) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .clickable {
                            onDismiss()
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Secondary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(10.dp)
                            .clickable {/* excluye la acrd del click del box*/ }
                    ) {
                        onContent()
                    }

                }
            }
            },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    )
}



