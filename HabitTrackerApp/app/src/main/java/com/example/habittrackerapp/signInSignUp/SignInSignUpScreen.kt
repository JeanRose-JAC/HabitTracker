package com.example.habittrackerapp.signInSignUp

import Routes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.SavedUserViewModel
import com.example.habittrackerapp.model.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.model.UserViewModel
import com.example.habittrackerapp.model.UserViewModelFactory

@Composable
fun SignSignUpScreen(savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory()),
                     MyViewModel: UserViewModel =viewModel(factory= UserViewModelFactory()),
                     authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())) {
    val myUiState by savedUserViewModel.uiState.collectAsState()
    val navController = LocalNavController.current
    val userInput= data.current
    val activeUser = MyViewModel.activeUser.collectAsState()
    var onceSigned by rememberSaveable { mutableStateOf(true) }

    Column {
        if( !myUiState.email.isEmpty()) {
            if (myUiState.email != "empty") {
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
                    navController.navigate(Routes.Setting.route)
                }
            }
            else{
                Text("Please")
                Button(onClick = {navController.navigate(Routes.SignUp.route)}) {
                    Text("Sign Up")
                }
                Text("or")

                Button(onClick = {navController.navigate(Routes.SignIn.route)}) {
                    Text("Sign In")
                }
            }
        }
    }

}