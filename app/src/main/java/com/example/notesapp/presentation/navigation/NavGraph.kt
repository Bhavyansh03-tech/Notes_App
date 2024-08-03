package com.example.notesapp.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.notesapp.presentation.navigation.screens.HomeScreen
import com.example.notesapp.presentation.navigation.screens.OnboardingScreen
import com.example.notesapp.presentation.navigation.screens.ScreenName
import com.example.notesapp.presentation.onboarding.OnBoardingScreen
import com.example.notesapp.presentation.onboarding.viewModel.OnboardingViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: ScreenName
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<ScreenName.AppStartNavigation>(
            startDestination = OnboardingScreen
        ){
            composable<OnboardingScreen>{
                // Initializing the view model :->
                val viewModel = hiltViewModel<OnboardingViewModel>()

                // Calling Onboarding Screen :->
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }

        navigation<ScreenName.HomeNavigator>(
            startDestination = HomeScreen
        ){
            composable<HomeScreen>{
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Home Screen",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }
    }
}