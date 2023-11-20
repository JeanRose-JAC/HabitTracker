package com.example.habittrackerapp.Settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.habittrackerapp.data


@Composable
fun SettingScreen(){
    Column {
        Button(onClick = { /*TODO*/ }) {
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

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Policy")
        }
    }
}