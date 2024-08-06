package com.example.notesapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.notesapp.data.room.model.NotesClass
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Upsert
    suspend fun upsertNote(notes: NotesClass)

    @Delete
    suspend fun deleteNote(notes: NotesClass)

    @Query("SELECT * FROM note ORDER BY dateAdded")
    fun getNotesOrderByDateAdded() : Flow<List<NotesClass>>

    @Query("SELECT * FROM note ORDER BY title ASC")
    fun getNotesOrderByTitle() : Flow<List<NotesClass>>

}