package com.example.habittrackerapp.noteInput

import android.widget.Toast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import com.example.habittrackerapp.LocalNavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.Note
import com.example.habittrackerapp.model.NotesViewModel
import com.example.habittrackerapp.model.NotesViewModelFactory

/**
 * It contains text-fields for user input and saves it to a list
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNote(id:String, notesViewModel: NotesViewModel = viewModel(factory= NotesViewModelFactory())) {

    val userInput= data.current

    val note by notesViewModel.currentNote.collectAsState()
    notesViewModel.getNote(id)
    var title by rememberSaveable { mutableStateOf(note.title) }
    var description by rememberSaveable { mutableStateOf(note.description) }
    var urlImage by rememberSaveable { mutableStateOf(note.urlImage!!) }
    val maxLength = 20
    var context = LocalContext.current;
    var navController= LocalNavController.current

    val scrollState = rememberScrollState()
    var openDialog1 by rememberSaveable { mutableStateOf(false)}
    var openDialog2 by rememberSaveable { mutableStateOf(false)}
    var clicked by rememberSaveable { mutableStateOf(false) };


    title=note.title;
    description=note.description;
    urlImage=note.urlImage!!


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp,16.dp,16.dp,2.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.padding(2.dp))
            {
                //title text field
                TextField(
                    value = title,
                    onValueChange = {
                        if (it.length <= maxLength) title = it
                        else Toast.makeText(
                            context,
                            "Cannot be more than 20 Characters",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    placeholder = { Text("Title") },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .padding(20.dp, 0.dp)
                        .fillMaxWidth()
                )
                //body/content description text field
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .height(250.dp)
                        .padding(20.dp, 0.dp)
                        .verticalScroll(scrollState),
                    placeholder = { Text("Description") },
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                )


                OutlinedTextField(
                    value = urlImage, onValueChange = { urlImage = it },
                    modifier = Modifier
                        .padding(10.dp, 40.dp, 10.dp, 10.dp)
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    singleLine = true,
                    placeholder = { Text("Enter Image URL...") }
                )


                //Buttons
                Row(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 20.dp, 0.dp)
                        .align(Alignment.End)
                )
                {
                    //saves input to the list
                    Button(
                        onClick =
                        {
                            clicked = true;
                            openDialog1 = true;
                        }, modifier = Modifier.padding(0.dp)
                    )
                    { Text("save") }
                    //goes back to list screen
                    Button(onClick = {
                        navController.navigate(Routes.ViewList.route)
                    }, modifier = Modifier.padding(10.dp, 0.dp)) { Text("cancel") }

                }
            }

        }
        Button(
            onClick = {
                clicked = true;
                openDialog2 = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp,0.dp),
            colors =  ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)

        )
        {
            Text(text = "remove")
        }
        if (clicked) {
            if (openDialog1) {
                AlertDialog(
                    onDismissRequest = { openDialog1 = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog1 = false
                                note.title = title;
                                note.description = description;
                                note.urlImage = urlImage;
                                notesViewModel.addNote(
                                    Note(
                                        note.title,
                                        note.description,
                                        note.urlImage,
                                        userInput.Email,
                                        note.id
                                    )
                                )
                                navController.navigate(Routes.ViewList.route)
                            }
                        ) { Text("confirm") }
                    },
                    title = {
                        Text("Edit")
                    },
                    text = {
                        Text(text = "Save the modified note of the title\n${title}")
                    }
                )
            }
            else if (openDialog2) {
                AlertDialog(
                    onDismissRequest = { openDialog2 = false },
                    title = {
                        Text("Delete")
                    },
                    text = {
                        Text(text = "Are you sure?" + note.title)
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog2 = false
                                notesViewModel.deleteNote(note.id)
                                navController.navigate(Routes.ViewList.route)
                            }
                        ) { Text("Yes") }
                    },
                    dismissButton = {
                        TextButton(onClick = { openDialog2 = false }) {
                            Text("No")
                        }
                    },
                )
            }
        }
    }
}


