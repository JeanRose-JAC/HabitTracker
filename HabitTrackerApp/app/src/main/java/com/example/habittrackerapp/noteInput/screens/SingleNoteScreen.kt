package com.example.habittrackerapp.noteInput.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.assignment3.singleNote.SingleNoteLayout
import com.example.habittrackerapp.LocalNotesList


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





