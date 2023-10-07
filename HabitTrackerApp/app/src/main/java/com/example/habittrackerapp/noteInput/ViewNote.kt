package com.example.habittrackerapp.noteInput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Main screen that contains adn displays the note design and list */
@Composable
fun ViewNote() {
    val scrollState = rememberScrollState()
    //scrollable
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        NoteLayout() //shows notePad

    }
}
