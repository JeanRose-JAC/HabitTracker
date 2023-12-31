package com.example.habittrackerapp.note

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
import com.example.habittrackerapp.LocalNotesList
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.noteViewModel.Note
import com.example.habittrackerapp.model.noteViewModel.NotesViewModel
import com.example.habittrackerapp.model.noteViewModel.NotesViewModelFactory

import java.util.Calendar


/**
 * It contains text-fields for user input and saves it to a list
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNote(notesViewModel: NotesViewModel = viewModel(factory= NotesViewModelFactory())) {

    val userInput= data.current
    var title by rememberSaveable { mutableStateOf("") }
    val maxLength = 20
    var urlImage by rememberSaveable { mutableStateOf("") }
    var context = LocalContext.current;
    var notesList= LocalNotesList.current
    var description by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val navController = LocalNavController.current
    Column() {
        Button(onClick = { navController.navigate(Routes.ViewList.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 2.dp)){
            Text("view List")
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp, 0.dp, 16.dp, 16.dp),
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
                    placeholder = { Text("$dayOfMonth/${month + 1}/$year") },
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
                    placeholder = { Text("Enter Text...") },
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
                    placeholder = { Text("Enter Image URL...") },
                    singleLine = true
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
                            if(title.isEmpty()){

                                title = "$dayOfMonth/${month + 1}/$year"
                            }
                            var popUp = addToList(title, description, urlImage, userInput.Email,notesList)
                            if(popUp){
                                val newNote = Note(title, description, urlImage, userInput.Email)
                                notesList.add(newNote);
                                notesViewModel.addNote(newNote)

                                Toast.makeText(
                                    context,
                                    "Note has been added to the list",
                                    Toast.LENGTH_LONG
                                ).show();

                                title = ""
                                description = ""
                                urlImage = ""
                            }
                            else{
                                Toast.makeText(
                                    context,
                                    "Missing input",
                                    Toast.LENGTH_LONG
                                ).show();
                            }

                        }, modifier = Modifier.padding(0.dp)
                    )
                    { Text("save") }
                    //clears user input
                    Button(onClick = {
                        title = ""
                        description = ""
                        urlImage = ""
                    }, modifier = Modifier.padding(10.dp, 0.dp)) { Text("clear") }
                }

            }
        }
    }

}

///adds the new notes to the list if the items aren't empty
fun addToList(title: String, description: String, urlImage:String?, email:String, notesList: SnapshotStateList<Note>):Boolean {

    if(title.isNotEmpty() && description.isNotEmpty()){
        val newNote = Note(title, description, urlImage, email)
        notesList.add(newNote)
        return true;
    }

    return false;
}



