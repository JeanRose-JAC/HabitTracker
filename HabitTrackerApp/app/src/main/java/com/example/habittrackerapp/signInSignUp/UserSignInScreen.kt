package com.example.habittrackerapp.signInSignUp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
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
    var wrongCredential by rememberSaveable { mutableStateOf(false)}

    val regex="""^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,63})${'$'}""".toRegex()

    Image(   painter = painterResource(R.drawable.logo2),
        contentDescription = null,
        modifier = Modifier.padding(start = 60.dp,top=20.dp)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top=300.dp)) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(30.dp,8.dp)
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
                .padding(30.dp,8.dp)
        ){
            TextField(
                value = password,
                onValueChange = {password=it},
                label={ Text("Please enter your Password") },
                isError = password.length<8,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
        Button(onClick = {
            authViewModel.signIn(email,password)
            MyViewModel.getUser(email,password)

//            if(activeUser.value.Password != password){
//                println("yes");
//                wrongCredential=true;
//            }

        }) {
            Text(text = "Sign in")

        }
        if(wrongCredential){
            Text(text = "Wrong username or password\nPlease try again", color = MaterialTheme.colorScheme.error)
        }
    }
    if(activeUser.value.FirstName.isNotEmpty()  && activeUser.value.Password == password){
        wrongCredential = false
        println("in signIn-> ${activeUser.value.FirstName}")
        userInput.FirstName=activeUser.value.FirstName
        userInput.Email = activeUser.value.Email
        userInput.Gender = activeUser.value.Gender
        userInput.LastName = activeUser.value.LastName
        userInput.ProfilePicture = activeUser.value.ProfilePicture
        userInput.Password = activeUser.value.Password
        savedUserViewModel.saveEmailAndPassword(userInput.Email, userInput.Password)
        navController.navigate(Routes.Profile.route)
    }
    else if(activeUser.value.Email == "empty"){
        wrongCredential = true
    }

}