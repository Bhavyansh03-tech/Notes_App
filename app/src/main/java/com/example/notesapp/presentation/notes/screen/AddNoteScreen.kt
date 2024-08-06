package com.example.notesapp.presentation.notes.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notesapp.presentation.notes.NotesState
import com.example.notesapp.presentation.notes.screen.topBar.AddNoteTopBar
import com.example.notesapp.presentation.notes.viewModel.NotesEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
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
            AddNoteTopBar(
                modifier = Modifier.fillMaxWidth(),
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        AddNotes(
            paddingValues = innerPadding,
            navController = navController,
            onEvent = onEvent,
            state = state,
        )
    }
}

@Composable
fun AddNotes(
    paddingValues: PaddingValues,
    navController: NavController,
    onEvent: (NotesEvent) -> Unit,
    state: NotesState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 10.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),

            value = state.title.value,
            onValueChange = {
                state.title.value = it
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.SemiBold
            ),
            colors = TextFieldDefaults.colors(

                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent

            ),
            placeholder = {
                Text(
                    text = "Title",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                    overflow = TextOverflow.Ellipsis
                )
            },
            maxLines = 2
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .align(Alignment.CenterHorizontally),

            value = state.description.value,
            onValueChange = {
                state.description.value = it
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.Medium
            ),
            colors = TextFieldDefaults.colors(

                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent

            ),
            placeholder = {
                Text(
                    text = "Description",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    overflow = TextOverflow.Ellipsis
                )
            },
            singleLine = false
        )

        Box(
            modifier = Modifier.weight(0.1f)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(50.dp),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    disabledContentColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.4f)
                ),
                onClick = {
                    onEvent(
                        NotesEvent.SaveNote(
                            title = state.title.value,
                            description = state.description.value
                        )
                    )
                    navController.popBackStack()
                }
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}