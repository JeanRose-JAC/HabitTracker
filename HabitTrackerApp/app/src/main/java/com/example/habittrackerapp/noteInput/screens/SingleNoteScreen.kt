package com.example.habittrackerapp.noteInput.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.habittrackerapp.noteInput.screens.singleNote.SingleNoteLayout
import com.example.habittrackerapp.LocalNotesList
import com.example.habittrackerapp.noteInput.EditLayout


@Composable
fun SingleNote(id: String) {
    Column{
        val noteList= LocalNotesList.current
        if(id.isBlank() || id.toIntOrNull()==null){
            SingleNoteLayout(noteList[noteList.lastIndex])
        }
        else{
            SingleNoteLayout(noteList[id.toInt()])
        }

    }
}
@Composable
fun SingleNoteEdit(id: String) {
    Column{
        val noteList= LocalNotesList.current
        if(id.isBlank() || id.toIntOrNull()==null){
            EditLayout(noteList[noteList.lastIndex])
        }
        else{
            EditLayout(noteList[id.toInt()])
        }

    }
}





