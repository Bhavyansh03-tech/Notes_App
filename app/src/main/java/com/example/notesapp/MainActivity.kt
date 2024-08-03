package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.presentation.navigation.NavGraph
import com.example.notesapp.presentation.viewModel.StartingViewModel
import com.example.notesapp.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<StartingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize splash screen
        val splashScreen = installSplashScreen()

        // Use coroutine scope to handle the splash condition
        val splashScope = CoroutineScope(Dispatchers.Main)
        splashScope.launch {
            viewModel.splashCondition.collect { splashCondition ->
                splashScreen.setKeepOnScreenCondition { splashCondition }
            }
        }

        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                val startDestination by viewModel.startDestination.collectAsState()
                val navController = rememberNavController()
                Box(
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
                ) {
                    if (startDestination != null) {
                        NavGraph(navController = navController, startDestination = startDestination!!)
                    }
                }
            }
        }
    }
}