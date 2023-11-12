package com.example.habittrackerapp.noteInput

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.R

/**
 * Main screen that contains adn displays the note design and list */
@Composable
fun ViewNote() {
    val scrollState = rememberScrollState()
    //scrollable
    val navController = LocalNavController.current

    Column(modifier = Modifier.verticalScroll(scrollState)) {

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
