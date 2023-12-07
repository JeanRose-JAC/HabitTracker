package com.example.habittrackerapp.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.habittrackerapp.R
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.model.noteViewModel.NotesViewModel
import com.example.habittrackerapp.model.noteViewModel.NotesViewModelFactory


@Composable
fun ViewSingleNote(id: String,
                   notesViewModel: NotesViewModel = viewModel(factory= NotesViewModelFactory())
) {
    val navController = LocalNavController.current
    val note by notesViewModel.currentNote.collectAsState()
    val scrollState = rememberScrollState()
    notesViewModel.getNote(id)
    Column(modifier = Modifier
        .padding(16.dp).verticalScroll(scrollState)){
        if (note.id == id) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            {
                Button(onClick = { navController.navigate(Routes.ViewList.route)},
                    modifier = Modifier
                        .padding(top=10.dp),
                    colors =  ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)) {
                    Text("View List",  modifier = Modifier.padding(start=30.dp,end=30.dp));
                }

                Button(
                    onClick = { navController.navigate("EditNoteScreenRoute/${note.id}") },
                    modifier = Modifier
                        .padding(start=10.dp,top=10.dp),
                    colors =  ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text(text = "edit",  modifier = Modifier
                        .padding(start=40.dp,end=40.dp))
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(16.dp, top = 2.dp, 16.dp, 16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                )
                {

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
        }
    }
}

@Composable
fun LoadImage(url:String) {
    AsyncImage(
        model = url,
        error= painterResource(id = R.drawable.no_image_placeholder),
        contentDescription = "user image",
        modifier = Modifier.padding(20.dp),
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center,
    )
}


