package com.example.habittrackerapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.auth.AuthViewModel
import com.example.habittrackerapp.auth.AuthViewModelFactory

@Composable
fun UserSignInScreen(modifier: Modifier = Modifier, authViewModel: AuthViewModel =
    viewModel(factory= AuthViewModelFactory())
){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

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
            println(email)
            println(password)
            authViewModel.signIn(email,password)
            //go to user profile
        }) {
            Text(text = "Sign in")
        }
    }

}