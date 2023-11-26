package com.example.habittrackerapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.SavedUser
import com.example.habittrackerapp.model.SavedUserViewModel
import com.example.habittrackerapp.model.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.model.UserViewModel
import com.example.habittrackerapp.model.UserViewModelFactory
import com.example.habittrackerapp.noteInput.ViewNote


@Composable
fun NoteScreen(savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory()),
               MyViewModel: UserViewModel =viewModel(factory= UserViewModelFactory()),
               authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())
) {
    val myUiState by savedUserViewModel.uiState.collectAsState()
    val navController = LocalNavController.current
    val userInput= data.current
    val activeUser = MyViewModel.activeUser.collectAsState()
    var onceSigned by rememberSaveable {mutableStateOf(true) }

    Column {
        if( !myUiState.email.isEmpty()){
            if(myUiState.email != "empty"){
                if(onceSigned){
                    authViewModel.signIn(myUiState.email,myUiState.password)
                    MyViewModel.getUser(myUiState.email)
                    onceSigned = false
                }

                if(activeUser.value.FirstName.isNotEmpty()){
                    println("in signIn-> ${activeUser.value.FirstName}")
                    userInput.FirstName=activeUser.value.FirstName
                    userInput.Email = activeUser.value.Email
                    userInput.Gender = activeUser.value.Gender
                    userInput.LastName = activeUser.value.LastName
                    userInput.ProfilePicture = activeUser.value.LastName
                    userInput.Password = activeUser.value.Password
                }

                if(userInput.Email.isNotEmpty()) {
                    Text(text = "Notes",
                        modifier = Modifier
                            .padding(20.dp, 10.dp)
                            .align(Alignment.CenterHorizontally),
                        style= MaterialTheme.typography.displaySmall)

                    ViewNote()
                }

            }
            else{
                AlertDialog(onDismissRequest = { /* Do nothing */ },
                    title = { Text("Please log in.") },
                    text = { Text("Log in to view your notes") },
                    confirmButton = {
                        TextButton(onClick = {navController.navigate(Routes.SignUpSignIn.route) }) {
                            Text("Close")
                        }
                    })
            }
        }

    }
}
