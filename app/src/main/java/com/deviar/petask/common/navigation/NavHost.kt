package com.deviar.petask.common.navigation


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deviar.petask.auth.ui.login.LoginScreen
import com.deviar.petask.auth.ui.register.RegisterScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import com.deviar.petask.calendar.CalendarScreen
import com.deviar.petask.pet.ui.PetScreen
import com.deviar.petask.tasks.TasksScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.deviar.petask.MainViewModel
import com.deviar.petask.R
import com.deviar.petask.profile.ProfileScreen
import kotlinx.coroutines.delay


@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    isLogged: Boolean,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val currentDestination =
        navController.currentBackStackEntryAsState()
            .value?.destination

    val showBar =
        currentDestination?.route in listOf(
            Pet::class.qualifiedName,
            Tasks::class.qualifiedName,
            Calendar::class.qualifiedName,
            Profile::class.qualifiedName
        )

    val startDestination =
        if (isLogged)
            Pet
        else
            Login

    Scaffold(
        topBar = {
            if (showBar) {
                PetaskTopAppBar(
                    navigateToProfile = { navController.navigate(Profile) },
                    navigateToLogin = { navController.navigate(Login) { popUpTo(0) } },
                    onLogoutClick = { mainViewModel.singOut() }
                )
            }
        },

        bottomBar = {
            if (showBar) {
                PetaskNavigationBar(
                    navController = navController,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = startDestination
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
                    petViewModel = hiltViewModel()
                )
            }

            composable<Tasks> {
                TasksScreen(

                )
            }

            composable<Calendar> {
                CalendarScreen()
            }

            composable<Profile> {
                ProfileScreen(
                    modifier = Modifier.padding(innerPadding),
                    profileViewModel = hiltViewModel(),
                    userPfp = painterResource(R.drawable.ic_user_mage)
                )
            }
        }
    }
}
