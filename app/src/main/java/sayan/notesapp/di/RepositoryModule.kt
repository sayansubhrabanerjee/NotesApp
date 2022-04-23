package sayan.notesapp.di

import sayan.notesapp.data.datasource.local.NoteDao
import sayan.notesapp.data.datasource.repository.NotesRepositoryImpl
import sayan.notesapp.domain.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun notesRepository(notesDao: NoteDao): NotesRepository = NotesRepositoryImpl(notesDao)
}