package com.example.habittrackerapp.noteInput

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
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.LocalNavController

/**
 * Formats the note elements to look prettier*/
@Composable
fun NoteLayout() {
    val navController = LocalNavController.current
    Column() {
        Button(onClick = { navController.navigate("NoteListScreenRoute") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp,0.dp,16.dp,2.dp)){
            Text("view List")
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp,0.dp,16.dp,16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            CreateNote()
        }
    }
}

@Composable
fun EditLayout(note: Note) {
    val navController = LocalNavController.current
    Column() {
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            EditNote(note)
        }

}


