package com.example.habittrackerapp.settings

import Routes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.darkMode


@Composable
fun SettingScreen() {
    val navController = LocalNavController.current


    Column {
        Button(onClick = { navController.navigate(Routes.Profile.route) }) {
            Text(text = "Profile")
        }

        SignOutUser();

        DeleteUser()
        Button(onClick = { navController.navigate(Routes.Policy.route) }) {
            Text(text = "Policy")
        }



        
    }
}

