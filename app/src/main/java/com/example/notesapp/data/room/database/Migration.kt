package com.example.notesapp.data.room.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Rename the existing table
        db.execSQL("ALTER TABLE note RENAME TO note_old")

        // Create a new table with the correct schema
        db.execSQL("""
            CREATE TABLE note (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                dateAdded TEXT NOT NULL
            )
        """)

        // Copy the data from the old table to the new table
        db.execSQL("""
            INSERT INTO note (id, title, description, dateAdded)
            SELECT id, title, description, dateAdded
            FROM note_old
        """)

        // Drop the old table
        db.execSQL("DROP TABLE note_old")
    }
}
