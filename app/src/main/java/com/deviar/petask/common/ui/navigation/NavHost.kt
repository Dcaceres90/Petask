package com.deviar.petask.common.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deviar.petask.auth.ui.login.LoginScreen
import com.deviar.petask.auth.ui.login.LoginViewModel
import com.deviar.petask.auth.ui.register.RegisterScreen
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NavHost(modifier: Modifier = Modifier){
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = Login){

        composable<Login>{
            LoginScreen(modifier = modifier,
                loginViewModel = viewModel(),
                navigateToRegister = {navController.navigate(Register)}
            )
        }

        composable<Register>{
            RegisterScreen(modifier = modifier,
                registerViewModel = viewModel()
                )
        }
    }
}
