package com.deviar.petask.common.ui.model

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val name: String,
    val icon: ImageVector,
    val route: Any
)
