package com.example.habittrackerapp.Settings

import Routes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.data


@Composable
fun SettingScreen(){
    val navController = LocalNavController.current
    val userInput = data.current;
    var popupControl by rememberSaveable { mutableStateOf(false) }

    Column {
        Button(onClick = { navController.navigate(Routes.Profile.route) }) {
            Text(text = "Profile")
        }

        SignOutUser();

        DeleteUser()

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Change Theme")
        }

        Button(onClick = { navController.navigate(Routes.Policy.route) }) {
            Text(text = "Policy")
        }
    }
}