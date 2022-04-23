package sayan.notesapp.data.datasource.repository

import sayan.notesapp.data.datasource.local.NoteDao
import sayan.notesapp.data.datasource.model.Note
import sayan.notesapp.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
): NotesRepository {

    override suspend fun insert(note: Note) = withContext(Dispatchers.IO) {
        noteDao.insert(note)
    }

    override fun getNotes(): Flow<List<Note>> = noteDao.getNotes()
}