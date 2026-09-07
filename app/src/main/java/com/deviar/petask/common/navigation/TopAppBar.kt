package com.deviar.petask.common.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.deviar.petask.MainViewModel
import com.deviar.petask.R
import com.deviar.petask.common.ui.components.UserImage
import com.deviar.petask.common.ui.theme.Brown
import com.deviar.petask.common.ui.theme.Creamy_light
import com.deviar.petask.common.ui.theme.GoldCoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetaskTopAppBar(
    userImageUri: String?,
    navigateToProfile: () -> Unit,
    navigateToLogin: () -> Unit,
    mainViewModel: MainViewModel
) {


    TopAppBar(
        title = { Text("Petask") },
        actions = {
            CoinConteiner(
                //coins
                mainViewModel.state.coins
            )
            DropdownMenu(
                navigateToProfile = navigateToProfile,
                navigateToLogin = navigateToLogin,
                userImageUri = userImageUri,
                onLogoutClick = { mainViewModel.singOut() }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun CoinConteiner(coinAmount: Int) {
    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(30.dp)
                .background(Creamy_light, RoundedCornerShape(50))
                .padding(start = 24.dp, end = 12.dp)
        ) {
            Text(
                text = coinAmount.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 10.dp),
                color = Brown,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Icon(
            painter = painterResource(R.drawable.ic_coin),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .offset(x = (-10).dp),
            tint = GoldCoin

        )
    }
}

@Composable
fun DropdownMenu(
    navigateToProfile: () -> Unit,
    navigateToLogin: () -> Unit,
    userImageUri: String?,
    onLogoutClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(Modifier.padding(horizontal = 12.dp)) {
        UserImage(
            imageUri = userImageUri,
            size = 48.dp,
            onClick = { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            containerColor = Creamy_light,
            shape = RoundedCornerShape(16.dp)
        ) {
            DropdownMenuItem(
                text = { Text("Profile", color = GoldCoin) },
                onClick = {
                    expanded = false
                    navigateToProfile()
                }
            )

            DropdownMenuItem(
                text = { Text("Log Out", color = Color.Red) },
                onClick = {
                    expanded = false
                    onLogoutClick()
                    navigateToLogin()
                }
            )
        }
    }
}