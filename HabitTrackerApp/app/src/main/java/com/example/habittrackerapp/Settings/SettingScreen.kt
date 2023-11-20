package com.example.habittrackerapp.Settings

import Routes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.data


@Composable
fun SettingScreen(){
    val navController = LocalNavController.current

    Column {
        Button(onClick = { navController.navigate(Routes.Profile.route) }) {
            Text(text = "Profile")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "signOut")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Delete Account")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Change Theme")
        }

        Button(onClick = { navController.navigate(Routes.Policy.route) }) {
            Text(text = "Policy")
        }
    }
}