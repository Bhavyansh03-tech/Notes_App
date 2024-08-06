package com.example.notesapp.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.data.room.dao.NotesDao
import com.example.notesapp.data.room.model.NotesClass

@Database(entities = [NotesClass::class], version = 2)
abstract class NotesDatabase: RoomDatabase() {
    abstract val dao: NotesDao
}