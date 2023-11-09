package com.example.habittrackerapp.noteInput.screens.singleNote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.habittrackerapp.R
import com.example.habittrackerapp.model.Note
import com.example.habittrackerapp.LocalNavController


@Composable
fun SingleNoteElementDisplay(note: Note) {
    val navController = LocalNavController.current
    Column(modifier = Modifier
        .padding(20.dp)
        .verticalScroll(rememberScrollState()))
    {
        Button(onClick = { navController.navigate(Routes.ViewList.route) }) {
            Text("View List");
        }
        LoadImage(url = note.urlImage!!)

        //title text field
        Text(
            text = note.title,
            modifier = Modifier
                .padding(20.dp, 0.dp)
                .fillMaxWidth()
        )
        //body/content description text field
        Text(
            text = note.description,
            modifier = Modifier
                .height(250.dp)
                .padding(20.dp, 0.dp),
        )
        //add remove button and edit at the top??
    }
}

@Composable
fun LoadImage(url:String) {
    AsyncImage(
        model = url,
        error= painterResource(id = R.drawable.no_image_placeholder),
        contentDescription = "user image",
        modifier = Modifier.padding(20.dp) )
}


