package com.deviar.petask.common.ui.navigation


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.deviar.petask.common.ui.model.NavItem
import com.deviar.petask.common.ui.theme.Primary
import com.deviar.petask.common.ui.theme.Secondary

@Composable
fun PetaskNavigationBar(navController: NavHostController, modifier: Modifier = Modifier) {

    val itemList = listOf(
        NavItem("Pet", Icons.Default.Pets, Pet),
        NavItem("Tasks", Icons.Default.Checklist, Tasks),
        NavItem("Calendar", Icons.Default.CalendarMonth, Calendar)
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination

    NavigationBar(
        containerColor = Primary,
        modifier = modifier
            .height(80.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        itemList.forEach { item ->
            NavigationBarItem(
                selected = currentRoute?.route == item.route::class.qualifiedName,
                onClick = {
                    if (currentRoute?.route != item.route::class.qualifiedName) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name,
                        tint = Secondary,
                        modifier = Modifier
                            .size(30.dp)
                            .border(
                                width = 2.dp,
                                color = Secondary,
                                shape = CircleShape
                            )
                            .padding(4.dp)
                    )
                },
                label = { Text(item.name, color = Secondary, fontWeight = FontWeight.ExtraBold) }
            )
        }
    }
}