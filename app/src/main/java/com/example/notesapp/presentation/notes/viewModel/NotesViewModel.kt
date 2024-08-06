package com.example.notesapp.presentation.notes.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.room.dao.NotesDao
import com.example.notesapp.data.room.model.NotesClass
import com.example.notesapp.presentation.notes.NotesState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class NotesViewModel(
    private val notesDao: NotesDao
): ViewModel() {

    private val isSortedByDateAdded = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    private var notes =
        isSortedByDateAdded.flatMapLatest { sort ->
            if (sort) {
                notesDao.getNotesOrderByDateAdded()
            } else {
                notesDao.getNotesOrderByTitle()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(NotesState())
    val state =
        combine(_state, isSortedByDateAdded, notes) { state, isSortedByDateAdded, notes ->
            state.copy(
                notes = notes,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotesState())

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNotes -> {
                viewModelScope.launch {
                    notesDao.deleteNote(event.notes)
                }
            }
            is NotesEvent.SaveNote -> {
                val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(System.currentTimeMillis())

                val note = NotesClass(
                    title = state.value.title.value,
                    description = state.value.description.value,
                    dateAdded = formattedDate.toLong()
                )
                viewModelScope.launch {
                    notesDao.upsertNote(note)
                }
                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        description = mutableStateOf("")
                    )
                }
            }
            NotesEvent.SortNotes -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }
        }
    }

}