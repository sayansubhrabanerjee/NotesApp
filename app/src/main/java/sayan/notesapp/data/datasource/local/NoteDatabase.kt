package sayan.notesapp.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import sayan.notesapp.data.datasource.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}