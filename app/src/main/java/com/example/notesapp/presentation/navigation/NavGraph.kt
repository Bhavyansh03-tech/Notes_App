package com.example.notesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.notesapp.presentation.navigation.screens.AddNote
import com.example.notesapp.presentation.navigation.screens.Home
import com.example.notesapp.presentation.navigation.screens.Onboarding
import com.example.notesapp.presentation.navigation.screens.ScreenName
import com.example.notesapp.presentation.notes.NotesState
import com.example.notesapp.presentation.notes.screen.AddNoteScreen
import com.example.notesapp.presentation.notes.screen.NotesScreen
import com.example.notesapp.presentation.notes.viewModel.NotesEvent
import com.example.notesapp.presentation.onboarding.OnBoardingScreen
import com.example.notesapp.presentation.onboarding.viewModel.OnboardingViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: ScreenName,
    state: NotesState,
    onEvent: (NotesEvent) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<ScreenName.AppStartNavigation>(
            startDestination = Onboarding
        ){
            composable<Onboarding>{
                // Initializing the view model :->
                val viewModel = hiltViewModel<OnboardingViewModel>()

                // Calling Onboarding Screen :->
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }

        navigation<ScreenName.HomeNavigator>(
            startDestination = Home
        ){
            composable<Home>{
                NotesScreen(
                    state = state,
                    onEvent = onEvent,
                    navController = navController
                )
            }

            composable<AddNote>{
                AddNoteScreen(
                    state = state,
                    onEvent = onEvent,
                    navController = navController
                )
            }
        }
    }
}