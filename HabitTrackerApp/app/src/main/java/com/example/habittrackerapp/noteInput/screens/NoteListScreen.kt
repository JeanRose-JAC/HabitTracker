package com.example.habittrackerapp.noteInput.screens

import androidx.compose.runtime.Composable
import com.example.habittrackerapp.LocalNotesList
import com.example.habittrackerapp.noteInput.DisplayNotesList


@Composable
fun NoteList() {
    val notesList= LocalNotesList.current
    DisplayNotesList(notesList = notesList)
}