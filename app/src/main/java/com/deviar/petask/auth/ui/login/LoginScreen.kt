package com.deviar.petask.auth.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.deviar.petask.common.ui.components.textfields.PetaskTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.R
import com.deviar.petask.common.ui.components.PetaskButton
import com.deviar.petask.common.ui.components.textfields.PasswordTextField
import com.deviar.petask.common.ui.components.texts.ClickableText
import com.deviar.petask.common.ui.theme.Black
import com.deviar.petask.common.ui.theme.Primary
import com.deviar.petask.common.ui.theme.Secondary

import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    modifier: Modifier,
    loginViewModel: LoginViewModel,
    navigateToRegister: () -> Unit,
    navigateToPet: () -> Unit
) {

    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val recoveryMessage = uiState.recoveryMessage
    LaunchedEffect(uiState.loginSuccess) {

        if (uiState.loginSuccess) {
            navigateToPet()
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Petask logo "
        )
        Spacer(Modifier.weight(1f))

        PetaskTextField(uiState.email, { loginViewModel.onEmailChanged(it) }, stringResource(R.string.textfield_user))
        PasswordTextField(uiState.password, { loginViewModel.onPasswordChanged(it) }, label = stringResource(R.string.textfield_password))

        ClickableText(stringResource(R.string.forgot_password), onClick = {loginViewModel.resetPassword()})
        if(recoveryMessage != null){

            Text(
                text = recoveryMessage,
                color = Primary
            )
        }
        Spacer(Modifier.weight(1f))

        PetaskButton(onClick = {loginViewModel.login2()}, text = stringResource(R.string.button_login), enabled = uiState.isLoginEnabled)
        Spacer(Modifier.weight(1f))

        LoginWithServicesContainer(loginViewModel)

        Spacer(Modifier.weight(2f))

        if(uiState.firebaseError != null){

            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = uiState.firebaseError!!,
                color = Color.Red
            )
        }

        ClickableText(stringResource(R.string.register), onClick = {navigateToRegister()})
    }
}

@Composable
fun LoginWithServicesContainer(loginViewModel: LoginViewModel) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Card(
        modifier = Modifier.height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Secondary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = Black
            )

            Text(
                text = stringResource(R.string.or_continue_with),
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = Black
            )
        }

        Image(
            painter = painterResource(R.drawable.google_button),
            contentDescription = "Continue with Google",
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    coroutineScope.launch {
                        loginViewModel.loginWithGoogle(context)
                    }
                }
        )
    }
}



