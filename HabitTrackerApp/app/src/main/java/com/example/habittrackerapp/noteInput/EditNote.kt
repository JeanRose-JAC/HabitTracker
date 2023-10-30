package com.example.habittrackerapp.noteInput

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.LocalNotesList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNote() {
    var title by rememberSaveable { mutableStateOf("") }
    val maxLength = 20
    var urlImage by rememberSaveable { mutableStateOf("") }
    var context = LocalContext.current;
    var notesList= LocalNotesList.current
    var navController= LocalNavController.current
    var description by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()
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
                    val popUp = addToList(
                        title = title,
                        description = description,
                        urlImage = urlImage,
                        notesList = notesList
                    )

                    title = ""
                    description = ""
                    urlImage = ""

                },
                modifier = Modifier.padding(0.dp),
            ) { Text("save") }
            //clears user input
            Button(onClick = {

            }, modifier = Modifier.padding(10.dp, 0.dp)) { Text("cancel") }
        }
    }
}