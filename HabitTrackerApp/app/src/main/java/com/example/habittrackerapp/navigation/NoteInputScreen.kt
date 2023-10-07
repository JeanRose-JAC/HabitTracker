package com.example.habittrackerapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.noteInput.ViewNote


@Composable
fun NoteScreen(){
    Column {
        Text(text = "Notes",
            modifier = Modifier
                .padding(20.dp,10.dp)
                .align(Alignment.CenterHorizontally),
            style= MaterialTheme.typography.displaySmall
        )
        ViewNote()
    }
}
