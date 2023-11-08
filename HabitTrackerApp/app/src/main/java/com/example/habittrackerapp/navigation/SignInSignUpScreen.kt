package com.example.habittrackerapp.navigation

import Routes
import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.data

@Composable
fun SignSignUpScreen() {
    val navController = LocalNavController.current
    val userInput= data.current
    //val submit= remember{ mutableStateOf(false) }

    if(Validate(firstName = userInput.FirstName, lastName = userInput.LastName, email = userInput.Email, password = userInput.Password)){
        //go to profileScreen
        Text( "yes")
    }
    else{
        Box(modifier = Modifier.fillMaxSize()) {
            Image(painter= painterResource(id = R.drawable.))
        }
            Column() {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)) {
                    Text(text = "insert fun image")

                }
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(0.dp, 16.dp)){
                    Text(text = "WELCOME MESSAGE")
                }

                Button(onClick = {navController.navigate(Routes.SignUp.route)},
                    colors=ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),) {
                    Text("GET STARTED")
                }

                Row(modifier = Modifier.align(CenterHorizontally)){
                    Text("Already have an account? ", modifier=Modifier.padding(14.dp,14.dp,0.dp,14.dp))
                    TextButton(onClick = {navController.navigate(Routes.SignIn.route)}) {
                        Text("Login")
                    }
                }

            
        
        
            
        
        



        }
        



    }

}