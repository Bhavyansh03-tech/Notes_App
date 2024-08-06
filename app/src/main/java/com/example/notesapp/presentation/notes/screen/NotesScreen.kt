package com.example.notesapp.presentation.notes.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notesapp.presentation.navigation.screens.AddNote
import com.example.notesapp.presentation.notes.NotesState
import com.example.notesapp.presentation.notes.screen.topBar.MainScreenTopBar
import com.example.notesapp.presentation.notes.viewModel.NotesEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavController,
    state: NotesState,
    onEvent: (NotesEvent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(MaterialTheme.colorScheme.background),

        topBar = {
            MainScreenTopBar(
                modifier = Modifier.fillMaxWidth(),
                scrollBehavior = scrollBehavior,
                onEvent = onEvent
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(AddNote) },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Notes"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 50.dp),
                paddingValues = innerPadding,
                onEvent = onEvent,
                state = state
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
            )
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onEvent: (NotesEvent) -> Unit,
    state: NotesState
) {
    LazyColumn(
        contentPadding = paddingValues,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.notes.size) { index ->
            NoteItem(
                state = state,
                index = index,
                onEvent = onEvent
            )
        }
    }
}