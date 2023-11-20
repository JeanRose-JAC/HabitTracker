package com.example.habittrackerapp.signInSignUp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory
import com.example.habittrackerapp.data
import com.example.habittrackerapp.model.SavedUserViewModel
import com.example.habittrackerapp.model.SavedUserViewModelSavedFactory
import com.example.habittrackerapp.model.UserViewModel
import com.example.habittrackerapp.model.UserViewModelFactory

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserSignInScreen(modifier: Modifier = Modifier,
    MyViewModel: UserViewModel =viewModel(factory= UserViewModelFactory()),
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory()),
    savedUserViewModel: SavedUserViewModel = viewModel(factory = SavedUserViewModelSavedFactory())
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val allUsers = MyViewModel.allUsers.collectAsState();
    val activeUser = MyViewModel.activeUser.collectAsState()
    var userInput= data.current
    val navController = LocalNavController.current

    val regex="""^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,63})${'$'}""".toRegex()

    Column {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            TextField(
                value = email,
                onValueChange = {email=it},
                label={ Text("Please enter your email") },
                isError=!email.matches(regex)

            )
        }
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            TextField(
                value = password,
                onValueChange = {password=it},
                label={ Text("Please enter your Password") },
                isError = password.length<8
            )
        }
        Button(onClick = {
            authViewModel.signIn(email,password)
            MyViewModel.getUser(email)

        }) {
            Text(text = "Sign in")
        }
    }
    if(activeUser.value.FirstName.isNotEmpty()){
        println("in signIn-> ${activeUser.value.FirstName}")
        userInput.FirstName=activeUser.value.FirstName
        userInput.Email = activeUser.value.Email
        userInput.Gender = activeUser.value.Gender
        userInput.LastName = activeUser.value.LastName
        userInput.ProfilePicture = activeUser.value.LastName
        userInput.Password = activeUser.value.Password
        savedUserViewModel.saveEmailAndPassword(userInput.Email, userInput.Password)
        navController.navigate(Routes.Profile.route)
    }

}