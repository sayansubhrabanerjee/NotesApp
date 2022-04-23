package sayan.notesapp.domain.repository

import sayan.notesapp.data.datasource.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun insert(note: Note)

    fun getNotes(): Flow<List<Note>>
}