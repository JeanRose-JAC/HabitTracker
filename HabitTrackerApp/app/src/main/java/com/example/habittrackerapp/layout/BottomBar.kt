package com.example.habittrackerapp.layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.example.habittrackerapp.LocalNavController


/**
 * The Bottom bar of the different screens show in the application, it shows a way to navigate to
 * the SignUp page, the About page, the Profile page and the Welcome page
 */
@Composable
fun BottomBarScreen() {
    val navController = LocalNavController.current


    BottomAppBar(
        actions = {
            IconButton(onClick = { navController.navigate(Routes.SignUp.route) }) {
                Icon(Icons.Filled.Create, contentDescription = "SignUp")
            }

        })

}