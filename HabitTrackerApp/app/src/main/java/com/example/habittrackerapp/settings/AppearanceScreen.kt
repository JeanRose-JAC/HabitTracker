package com.example.habittrackerapp.settings

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.habittrackerapp.LocalNavController

@Composable
fun Appearance() {
    val navController = LocalNavController.current
    AlertDialog(onDismissRequest = {navController.popBackStack() },
        title = { Text("Feature to come") },
        text = { Text("Please wait for future updates") },
        modifier = Modifier,
        confirmButton = {
            TextButton(onClick ={navController.popBackStack()} ) {
                Text("Ok")
            }
        })
}
