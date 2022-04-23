package sayan.notesapp.di

import android.app.Application
import androidx.room.Room
import sayan.notesapp.data.datasource.local.NoteDao
import sayan.notesapp.data.datasource.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun noteDatabase(application: Application): NoteDatabase =
        Room.databaseBuilder(application, NoteDatabase::class.java, "NoteDatabase")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun noteDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()
}