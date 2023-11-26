package com.example.habittrackerapp.noteInput

import Routes
import coil.compose.AsyncImage
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import com.example.habittrackerapp.R
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.toMutableStateList
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import com.example.habittrackerapp.LocalNavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.Note
import com.example.habittrackerapp.model.NotesViewModel
import com.example.habittrackerapp.model.NotesViewModelFactory

/**
 * Displays all the elements of the StringBuilder list in a card container*/
@Composable
fun DisplayNotesList(notesViewModel: NotesViewModel = viewModel(factory= NotesViewModelFactory()))
{
    val userInput= data.current
    val allNotes by notesViewModel.allNotes.collectAsState()
    val notesList by notesViewModel.allUserNotes.collectAsState()
    val navController= LocalNavController.current
    var openDialog by rememberSaveable { mutableStateOf(true)}
    var clicked by rememberSaveable { mutableStateOf(false) };
    LazyColumn{
        if(allNotes.isNotEmpty()){
            notesViewModel.getNotes(userInput.Email)
        }

        item{
            if(notesList.isEmpty()){
                Text("You have no notes saved.")
                Button(onClick = {navController.navigate(Routes.Note.route)}){
                    Text(text = "Add note")
                }
            }
        }
            itemsIndexed(notesList) { index, note ->
                Card(  modifier = Modifier
                    .padding(16.dp),
                    shape = RoundedCornerShape(7.dp))
                {

                    Row(modifier = Modifier.padding(10.dp)) {

                        //makes the image into an icon
                        AsyncImage(
                            model = note.urlImage,
                            error= painterResource(id = R.drawable.no_image_placeholder),
                            modifier = Modifier
                                .size(5.dp)
                                .padding(5.dp)
                                .clip(MaterialTheme.shapes.small),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                        Column(modifier = Modifier
                            .clickable {
                                navController.navigate("SingleNoteScreenRoute/${note.id}")
                            }
                            .fillMaxWidth()
                            .padding(10.dp))
                        {
                            Text(
                                text = note.title,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = note.description, fontFamily = FontFamily.Monospace
                            )
                            Row(modifier = Modifier
                                .align(Alignment.End))
                            {

                                Button(onClick = { navController.navigate("EditNoteScreenRoute/${note.id}")},
                                    modifier = Modifier
                                        .padding(0.dp)){
                                    Text(text = "edit")
                                }
                                Button(onClick = {clicked=true},
                                    modifier = Modifier
                                        .padding(10.dp,0.dp))
                                {
                                    Text(text = "remove")
                                }
                                if(clicked){
                                    if(openDialog){
                                        AlertDialog(
                                            onDismissRequest = {  openDialog=false },
                                            confirmButton = {
                                                TextButton(
                                                    onClick = {
                                                        openDialog=false
                                                        notesViewModel.deleteNote(note.id)
                                                        if(notesList.isEmpty()){
                                                            navController.navigate(Routes.Note.route)
                                                        }

                                                    }
                                                ) {Text("confirm")}
                                            },
                                            title={
                                                Text("Delete")
                                            },
                                            text = {
                                                Text(text = "Are you sure?")
                                            }
                                        )
                                    }
                                }

                            }

                        }
                    }

                }

                Divider(modifier = Modifier.fillMaxWidth())//Separates each item
            }

    }

}


//Taken from slide 30 AppDev2_Day_11-12-13_final
@Composable
fun <T: Any> rememberMutableStateListOf(vararg elements: T): SnapshotStateList<T> {
    return rememberSaveable(
        saver = listSaver(
            save = { stateList ->
                if (stateList.isNotEmpty()) {
                    val first = stateList.first()
                    if (!canBeSaved(first)) {
                        throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                    }
                }
                stateList.toList()
            },
            restore = { it.toMutableStateList() }
        )
    ) {
        elements.toList().toMutableStateList()
    }
}

