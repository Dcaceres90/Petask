package com.deviar.petask.common.ui.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deviar.petask.auth.ui.login.LoginScreen
import com.deviar.petask.auth.ui.register.RegisterScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.deviar.petask.calendar.CalendarScreen
import com.deviar.petask.pet.PetScreen
import com.deviar.petask.tasks.TasksScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    isLogged: Boolean
) {

    val navController = rememberNavController()

    val startDestination =
        if (isLogged)
            Pet
        else
            Login

    Scaffold(

        topBar = {
            // después hacemos la top bar
        },

        bottomBar = {

            val currentDestination =
                navController.currentBackStackEntryAsState()
                    .value?.destination

            val showBottomBar =
                currentDestination?.route in listOf(
                    Pet::class.qualifiedName,
                    Tasks::class.qualifiedName,
                    Calendar::class.qualifiedName
                )

            if (showBottomBar) {
                PetaskNavigationBar(
                    navController = navController,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(innerPadding)
        ) {

            composable<Login> {
                LoginScreen(
                    modifier = modifier,
                    loginViewModel = hiltViewModel(),
                    navigateToRegister = {
                        navController.navigate(Register)
                    },
                    navigateToPet = {
                        navController.navigate(Pet) {
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
                    registerViewModel = hiltViewModel(),
                    navigateToPet = {
                        navController.navigate(Pet) {
                            popUpTo(Login) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable<Pet> {
                PetScreen(
                    petViewModel = hiltViewModel(),
                    navigateToLogin = {
                        navController.navigate(Login) {
                            popUpTo(0)
                        }
                    }
                )
            }

            composable<Tasks> {
                TasksScreen()
            }

            composable<Calendar> {
                CalendarScreen()
            }
        }
    }
}
