package sayan.notesapp.domain.usecase

import sayan.notesapp.data.datasource.model.Note
import sayan.notesapp.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend fun insert(note: Note) = notesRepository.insert(note)

    fun getNotes(): Flow<List<Note>> = notesRepository.getNotes()
}