package com.example.habittrackerapp.settings

import Routes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.habittrackerapp.LocalNavController


/**
 * This is the main Setting screen that allows the user to signOut
 * of their account, see their profile information, delete their account
 * or go see the app policy
 */

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