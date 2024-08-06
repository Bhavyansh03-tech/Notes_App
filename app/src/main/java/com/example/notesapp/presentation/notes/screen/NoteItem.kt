package com.example.notesapp.presentation.notes.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesapp.presentation.notes.NotesState
import com.example.notesapp.presentation.notes.viewModel.NotesEvent

@Composable
fun NoteItem(
    state: NotesState,
    index: Int,
    onEvent: (NotesEvent) -> Unit
) {
    // Check If Favorite Button Is Clicked or Not.
    var clicked by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(40.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 20.dp, bottom = 15.dp, end = 38.dp)
        ) {
            Text(
                text = state.notes[index].title,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = state.notes[index].description,
                color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(17.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 11.dp, end = 40.dp, top = 6.dp, bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccessTime,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.width(7.dp))

                Text(
                    text = state.notes[index].dateAdded,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(0.2f)
                .padding(top = 20.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                onClick = { clicked = !clicked }
            ) {
                Icon(
                    imageVector = if (clicked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = "Delete Note",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                onClick = {
                    onEvent(NotesEvent.DeleteNotes(state.notes[index]))
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete Note",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}