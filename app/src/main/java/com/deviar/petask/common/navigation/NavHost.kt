package com.deviar.petask.common.navigation


import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deviar.petask.auth.ui.login.LoginScreen
import com.deviar.petask.auth.ui.register.RegisterScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import com.deviar.petask.pet.ui.PetScreen
import com.deviar.petask.tasks.TasksScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.deviar.petask.MainViewModel
import com.deviar.petask.createpet.CreatePetScreen
import com.deviar.petask.goals.ui.GoalsScreen
import com.deviar.petask.onboarding.ui.OnboardingScreen
import com.deviar.petask.profile.ProfileScreen
import com.deviar.petask.profile.ProfileViewModel


@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    isLogged: Boolean,
    hasUserModel: Boolean,
    hasPetModel: Boolean,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val navController = rememberNavController()

    val profileViewModel: ProfileViewModel = hiltViewModel()


    val currentDestination =
        navController.currentBackStackEntryAsState()
            .value?.destination

    val showBar =
        currentDestination?.route in listOf(
            Pet::class.qualifiedName,
            Tasks::class.qualifiedName,
            Goals::class.qualifiedName,
            Profile::class.qualifiedName
        )

    val startDestination =
        if (!isLogged) {
            Login
        } else if (!hasUserModel) {
            Onboarding
        } else if (!hasPetModel) {
            CreatePet
        } else {
            Pet
        }

    Scaffold(
        topBar = {
            if (showBar) {
                PetaskTopAppBar(
                    coins = mainViewModel.state.coins,
                    userImageUri = profileViewModel.state.imageUri,
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
                    navigateToOnboarding = {
                        navController.navigate(Onboarding) {
                            popUpTo(Login) {
                                inclusive = true
                            }
                        }
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
                    navigateToOnboarding = {
                        navController.navigate(Onboarding) {
                            popUpTo(Login) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable <Onboarding>{
                OnboardingScreen(
                    onboardingViewModel = hiltViewModel(),
                    navigateToPet = {
                        navController.navigate(Pet)
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

            composable<Goals> {
                GoalsScreen(
                    modifier = Modifier.padding(innerPadding),
                    goalsViewModel = hiltViewModel()
                )

            }

            composable<Profile> {
                ProfileScreen(
                    modifier = Modifier.padding(innerPadding),
                    profileViewModel = profileViewModel
                )
            }

            composable<CreatePet> {
                CreatePetScreen(
                    createPetViewModel = hiltViewModel(),
                    navigateToPet = {
                        navController.navigate(Pet) {
                            popUpTo(CreatePet) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}
