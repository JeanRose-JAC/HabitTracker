package com.example.habittrackerapp.noteInput.screens.singleNote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.habittrackerapp.R
import com.example.habittrackerapp.model.Note
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.model.NotesViewModel
import com.example.habittrackerapp.model.NotesViewModelFactory


@Composable
fun SingleNoteElementDisplay(id: String,
                             notesViewModel: NotesViewModel = viewModel(factory= NotesViewModelFactory())
) {
    val navController = LocalNavController.current
    var openDialog by rememberSaveable { mutableStateOf(false) }
    val note by notesViewModel.currentNote.collectAsState()
    notesViewModel.getNote(id)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ){
    Column(modifier = Modifier
        .padding(20.dp)
        .verticalScroll(rememberScrollState()))
    {
        Button(onClick = { navController.navigate(Routes.ViewList.route) }) {
            Text("View List");
        }

        if (note.id == id) {
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

            Button(
                onClick = {
                    openDialog = true
                },
                modifier = Modifier
                    .padding(10.dp, 0.dp)
            )
            {
                Text(text = "remove")
            }

            if (openDialog) {
                println(note.title)
                AlertDialog(
                    onDismissRequest = { openDialog = false },
                    title = {
                        Text("Delete")
                    },
                    text = {
                        Text(text = "Are you sure?" + note.title)
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog = false
                                notesViewModel.deleteNote(note.id)
                                navController.navigate(Routes.ViewList.route)
                            }
                        ) { Text("Yes") }
                    },
                    dismissButton = {
                        TextButton(onClick = { openDialog = false }) {
                            Text("No")
                        }
                    },
                )
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
        modifier = Modifier.padding(20.dp) )
}


