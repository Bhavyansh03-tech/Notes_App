package com.example.notesapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.domain.useCases.AppEntryUseCases
import com.example.notesapp.presentation.navigation.screens.ScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartingViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    private val _splashCondition = MutableStateFlow(true)
    val splashCondition: StateFlow<Boolean> = _splashCondition

    private val _startDestination = MutableStateFlow<ScreenName?>(null)
    val startDestination: StateFlow<ScreenName?> = _startDestination

    init {
        viewModelScope.launch {
            appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
                _startDestination.value = if (shouldStartFromHomeScreen) ScreenName.HomeNavigator else ScreenName.AppStartNavigation
                delay(100)
                _splashCondition.value = false
            }.launchIn(this)
        }
    }
}