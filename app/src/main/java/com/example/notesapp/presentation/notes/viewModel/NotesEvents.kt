package com.example.notesapp.presentation.notes.viewModel

import com.example.notesapp.data.room.model.NotesClass

sealed interface NotesEvent {

    object SortNotes: NotesEvent
    data class DeleteNotes(val notes: NotesClass): NotesEvent
    data class SaveNote(
        val title: String,
        val description: String
    ): NotesEvent

}