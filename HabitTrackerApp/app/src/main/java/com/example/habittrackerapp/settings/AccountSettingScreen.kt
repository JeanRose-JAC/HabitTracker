package com.example.habittrackerapp.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.noteViewModel.Note
import com.example.habittrackerapp.model.noteViewModel.NotesViewModel
import com.example.habittrackerapp.model.noteViewModel.NotesViewModelFactory
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModel
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.model.userViewModel.UserViewModel
import com.example.habittrackerapp.model.userViewModel.UserViewModelFactory

/**
 * The AccountSetting gets the user information
 * and displays their email and password and allow the user to edit those different information
 * it also contain the policy and the option to delete their account with validation
 */
@Composable
fun AccountSetting(myViewModel: UserViewModel =
                       viewModel(factory= UserViewModelFactory()),
                   authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory()),
                   savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory()),
                   notesViewModel: NotesViewModel = viewModel(factory= NotesViewModelFactory())) {
    val navController = LocalNavController.current
    val userInput = data.current
    var email by rememberSaveable { mutableStateOf(userInput.Email) }
    var password by rememberSaveable { mutableStateOf(userInput.Password) }
    var saveChanges by rememberSaveable { mutableStateOf(false) }
    val allNotes by notesViewModel.allNotes.collectAsState()
    val notesList by notesViewModel.allUserNotes.collectAsState()

    val focusManager = LocalFocusManager.current

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            if(allNotes.isNotEmpty()){
                notesViewModel.getNotes(userInput.Email)
            }

            Text(
                text = "Account Settings",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium,
                textAlign =  TextAlign.Center
            )

            Button(onClick = {navController.navigate(Routes.Policy.route)},
                colors =  ButtonDefaults.buttonColors(containerColor =  Color(138, 154, 91)),
                modifier = Modifier.fillMaxWidth().padding(20.dp,0.dp,20.dp,20.dp)
            ){
                Text(text = "Policy")
            }

            Box(modifier = Modifier.fillMaxWidth().background(Color(243, 240, 228)), contentAlignment = Alignment.Center){
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text("Change: ", fontSize = 18.sp, modifier = Modifier.fillMaxWidth(), textAlign =  TextAlign.Center)
                    TextCard("Email",email, focusManager) { email = it }
                    TextCard("Password",password, focusManager) { password = it }

                    if(ValidUserProfileSettingChanges(email,password)){
                        Button(onClick = {saveChanges=true},modifier= Modifier.fillMaxWidth().padding(start=20.dp, top = 20.dp,end=20.dp)) {
                            Text("Save Changes")
                        }
                        if(saveChanges){
                            SaveUserProfileChange(userInput.FirstName,userInput.LastName,email,password,userInput.ProfilePicture)
                            notesList.forEach(){
                                notesViewModel.addNote(Note(it.title, it.description, it.urlImage, email, it.id))
                            }
                            focusManager.clearFocus()
                            saveChanges = false
                        }
                    }


                    Button(
                        onClick = {navController.navigate(Routes.Profile.route)},
                        colors =  ButtonDefaults.buttonColors(containerColor =  Color(138, 154, 91)),
                        modifier = Modifier.padding(20.dp)){
                        Text(text = "Cancel", modifier = Modifier.padding(40.dp,0.dp))
                    }
                }

            }
            DeleteUser()
        }

    }
}