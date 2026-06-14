package com.deviar.petask.common.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deviar.petask.auth.ui.login.LoginScreen
import com.deviar.petask.auth.ui.register.RegisterScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deviar.petask.pet.PetScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun NavHost(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()

    val startDestination =
        if (Firebase.auth.currentUser != null)
            Pet
        else
            Login

    NavHost(navController = navController, startDestination = startDestination) {

        composable<Login> {
            LoginScreen(
                modifier = modifier,
                loginViewModel = viewModel(),
                navigateToRegister = { navController.navigate(Register) },
                navigateToPet = {
                    navController.navigate(Pet)
                    {
                        popUpTo(Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Register> {
            RegisterScreen(
                modifier = modifier,
                registerViewModel = viewModel(),
                navigateToPet = {
                    navController.navigate(Pet)
                    {
                        popUpTo(Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Pet> {
            PetScreen(
                petViewModel = viewModel(),
                navigateToLogin = {
                    navController.navigate(Login) {
                        popUpTo(Pet) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
