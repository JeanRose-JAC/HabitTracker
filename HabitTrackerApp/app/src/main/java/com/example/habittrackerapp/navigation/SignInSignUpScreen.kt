package com.example.habittrackerapp.navigation

import Routes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.data

@Composable
fun SignSignUpScreen(){
    val navController = LocalNavController.current
    val userInput= data.current
    //val submit= remember{ mutableStateOf(false) }

    if(Validate(firstName = userInput.FirstName, lastName = userInput.LastName, email = userInput.Email, password = userInput.Password)){
        //go to profileScreen
        Text( "yes")
    }
    else{
        Column {
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