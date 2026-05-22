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
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.R
import com.deviar.petask.common.ui.components.PetaskButton
import com.deviar.petask.common.ui.components.textfields.PasswordTextField
import com.deviar.petask.common.ui.components.texts.ClickableText
import com.deviar.petask.common.ui.theme.Black
import com.deviar.petask.common.ui.theme.Secondary


@Composable
fun LoginScreen(
    modifier: Modifier,
    loginViewModel: LoginViewModel
) {

    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

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
        PasswordTextField(uiState.password, { loginViewModel.onPasswordChanged(it) })

        ClickableText(stringResource(R.string.forgot_password), onClick = {})
        Spacer(Modifier.weight(1f))

        PetaskButton(onClick = {loginViewModel.login()}, text = stringResource(R.string.button_login), enabled = uiState.isLoginEnabled)
        Spacer(Modifier.weight(1f))

        LoginWithServicesContainer()

        Spacer(Modifier.weight(2f))

        ClickableText(stringResource(R.string.register), onClick = {})
    }
}

@Composable
fun LoginWithServicesContainer() {
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
                ) { }
        )
    }
}



