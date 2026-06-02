package com.deviar.petask.auth.ui.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.R
import com.deviar.petask.common.ui.components.PetaskButton
import com.deviar.petask.common.ui.components.textfields.PasswordTextField
import com.deviar.petask.common.ui.components.textfields.PetaskTextField
import com.deviar.petask.common.ui.components.texts.ClickableText
import com.deviar.petask.common.ui.theme.Primary

@Composable
fun RegisterScreen(
    modifier: Modifier,
    registerViewModel: RegisterViewModel,
    navigateToPet: () -> Unit
) {
    val uiState by registerViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState.registerSuccess) {

        if (uiState.registerSuccess) {
            navigateToPet()
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            stringResource(R.string.title_create_your_account),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 32.sp,
            color = Primary

        )
        Spacer(Modifier.weight(1f))
        PetaskTextField(uiState.email, {registerViewModel.onEmailChanged(it)}, stringResource(R.string.textfield_user), error = uiState.emailError) //email
        PasswordTextField(uiState.password, {registerViewModel.onPasswordChanged(it) }, label = stringResource(R.string.label_create_your_password), error = uiState.passwordError) //contraseña
        PasswordTextField(uiState.confirmedPassword, { registerViewModel.onConfirmPasswordChanged(it)}, label= stringResource(R.string.label_repeat_your_password), error = uiState.confirmPasswordError) //repite contraseña
        PetaskButton({registerViewModel.register()}, stringResource(R.string.register), enabled = uiState.isRegisterEnabled)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = uiState.termsAndConditions,
                onCheckedChange = { registerViewModel.onTermsAccepted(it)}
            )

            ClickableText(stringResource(R.string.terms_and_Conditions), onClick = {})

        }
        Spacer(Modifier.weight(2f))
        if(uiState.firebaseError != null){

            Text(
                text = uiState.firebaseError!!,
                color = Color.Red
            )
        }
    }


}