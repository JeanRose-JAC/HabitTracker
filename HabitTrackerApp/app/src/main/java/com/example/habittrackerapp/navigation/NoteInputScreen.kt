package com.example.habittrackerapp.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.R
import com.example.habittrackerapp.noteInput.ViewNote


@Composable
fun NoteScreen() {
    Image(painter= painterResource(R.drawable.background),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        alpha = 0.45F)
    Column {
        ViewNote()
    }
}
