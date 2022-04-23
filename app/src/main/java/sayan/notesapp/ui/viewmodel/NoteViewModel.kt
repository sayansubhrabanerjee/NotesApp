package sayan.notesapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import sayan.notesapp.data.datasource.model.Note
import sayan.notesapp.domain.usecase.NotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase
) : ViewModel() {

    val notes: MutableState<List<Note>> = mutableStateOf(emptyList())

    init {
        getNotes()
    }

    fun insert(note: Note) = viewModelScope.launch {
        notesUseCase.insert(note)
    }

    private fun getNotes() = viewModelScope.launch {
        notesUseCase.getNotes()
            .catch {
                Log.i("mytest: ", "Error getting Notes: ")
            }
            .collectLatest {
                notes.value = it
            }
    }
}