package com.example.habittrackerapp.note

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModel
import com.example.habittrackerapp.model.userViewModel.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.model.userViewModel.UserViewModel
import com.example.habittrackerapp.model.userViewModel.UserViewModelFactory

/**
 * This is the main screen that is shown when first launching the app, it contains the create note composable.
 * Checks if the user is logged in or not, if yes show the create composable,
 * else shows an alert dialogue that redirects them to the login page
 * */
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
    val scrollState = rememberScrollState()
    Image(painter= painterResource(R.drawable.background),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize(),
        alpha = 0.45F)

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        if( !myUiState.email.isEmpty()){
            if(myUiState.email != "empty"){
                if(onceSigned){
                    authViewModel.signIn(myUiState.email,myUiState.password)
                    MyViewModel.getUser(myUiState.email, myUiState.password)
                    onceSigned = false
                }

                if(activeUser.value.FirstName.isNotEmpty()){
                    println("in signIn-> ${activeUser.value.FirstName}")
                    userInput.FirstName=activeUser.value.FirstName
                    userInput.Email = activeUser.value.Email
                    userInput.Gender = activeUser.value.Gender
                    userInput.LastName = activeUser.value.LastName
                    userInput.ProfilePicture = activeUser.value.ProfilePicture
                    userInput.Password = activeUser.value.Password
                     
                }

                if(userInput.Email.isNotEmpty()) {
                    Text(text = "Notes",
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign =  TextAlign.Center)

                    CreateNote()
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


