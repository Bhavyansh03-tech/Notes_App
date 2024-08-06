package com.example.notesapp.presentation.navigation.screens

import kotlinx.serialization.Serializable

sealed class ScreenName {
    @Serializable data object AppStartNavigation : ScreenName()
    @Serializable data object HomeNavigator : ScreenName()
}

@Serializable object Home
@Serializable object AddNote
@Serializable object Onboarding