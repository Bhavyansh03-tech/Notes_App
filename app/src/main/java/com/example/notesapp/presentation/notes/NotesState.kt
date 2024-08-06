package com.example.notesapp.presentation.notes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.notesapp.data.room.model.NotesClass

data class NotesState(
    val notes: List<NotesClass> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val description: MutableState<String> = mutableStateOf("")
)
