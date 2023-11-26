package com.example.habittrackerapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel (private val noteRepository: NoteRepository) : ViewModel() {
    // private UI state (MutableStateFlow)
    private val _currentNote = MutableStateFlow(Note())
    // public getter for the state (StateFlow)
    val currentNote: StateFlow<Note> = _currentNote.asStateFlow()

    private val _allNotes = MutableStateFlow(listOf<Note>())
    // public getter for the state (StateFlow)
    val allNotes: StateFlow<List<Note>> = _allNotes.asStateFlow()

    private val _allUserNotes = MutableStateFlow(listOf<Note>())
    // public getter for the state (StateFlow)
    val allUserNotes: StateFlow<List<Note>> = _allNotes.asStateFlow()

    init {
        // Start collecting the data from the data store when the ViewModel is created.
        viewModelScope.launch {
            noteRepository.getNotes().collect { allNotes ->
                _allNotes.value = allNotes
            }
        }
    }

    fun addNote(note:Note) {
        viewModelScope.launch {
            _currentNote.update { note}
            noteRepository.saveNote(_currentNote.value.id, _currentNote.value)
        }
    }

    fun getNotes(owner: String){
        _allUserNotes.value = _allNotes.value.filter { it.owner == owner }
    }

    fun getNote(id: String){
        viewModelScope.launch{
            noteRepository.getNote(id).collect{ note ->
                _currentNote.update { note }
            }
        }
    }

    fun deleteNote(id: String){
        viewModelScope.launch {
            noteRepository.deleteNote(id)
        }
    }

}

/* ViewModel Factory that will create our view model by injecting the
      ProfileDataStore from the module.
 */
class NotesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(MyApp.appModule.noteRepository) as T
    }
}