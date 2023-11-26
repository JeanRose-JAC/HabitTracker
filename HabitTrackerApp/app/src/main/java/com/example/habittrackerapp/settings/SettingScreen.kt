package com.example.habittrackerapp.settings

import Routes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.habittrackerapp.LocalNavController


@Composable
fun SettingScreen() {
    val navController = LocalNavController.current

    Column {
        Button(onClick = { navController.navigate(Routes.Profile.route) }) {
            Text(text = "Profile")
        }

        SignOutUser();

        DeleteUser()

        Button(onClick = { TODO()}) {
            Text(text = "Change Theme")
        }

        Button(onClick = { navController.navigate(Routes.Policy.route) }) {
            Text(text = "Policy")
        }
        
    }
}