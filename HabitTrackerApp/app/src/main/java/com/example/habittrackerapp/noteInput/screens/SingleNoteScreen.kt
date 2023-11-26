package com.example.habittrackerapp.noteInput.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.noteInput.screens.singleNote.SingleNoteLayout
import com.example.habittrackerapp.LocalNotesList
import com.example.habittrackerapp.model.NotesViewModel
import com.example.habittrackerapp.model.NotesViewModelFactory
import com.example.habittrackerapp.noteInput.EditLayout


@Composable
fun SingleNote(id: String, notesViewModel: NotesViewModel = viewModel(factory= NotesViewModelFactory())) {
    val notesList by notesViewModel.allNotes.collectAsState()

    Column{
        if(notesList.isNotEmpty()){
            SingleNoteLayout(notesList.first { it.id == id })
        }

    }
}
@Composable
fun SingleNoteEdit(id: String, notesViewModel: NotesViewModel = viewModel(factory= NotesViewModelFactory())) {
    val notesList by notesViewModel.allNotes.collectAsState()

    Column{
        if(notesList.isNotEmpty()) {
            EditLayout(notesList.first { it.id == id })
        }
    }
}





