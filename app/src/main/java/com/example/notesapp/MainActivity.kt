package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.notesapp.data.room.database.NotesDatabase
import com.example.notesapp.presentation.navigation.NavGraph
import com.example.notesapp.presentation.mainViewModel.MainViewModel
import com.example.notesapp.presentation.notes.viewModel.NotesViewModel
import com.example.notesapp.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes.db"
        ).build()
    }
    private val notesViewModel by viewModels<NotesViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NotesViewModel(database.dao) as T
                }
            }
        }
    )

    @OptIn(ExperimentalMaterial3Api::class)
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
                val state by notesViewModel.state.collectAsState()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    if (startDestination != null) {
                        NavGraph(
                            navController = navController,
                            startDestination = startDestination!!,
                            state = state,
                            onEvent = notesViewModel::onEvent
                        )
                    }
                }
            }
        }
    }
}