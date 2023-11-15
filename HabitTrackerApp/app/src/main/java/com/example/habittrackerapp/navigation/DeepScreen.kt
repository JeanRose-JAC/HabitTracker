package com.example.habittrackerapp.navigation


import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.example.habittrackerapp.layout.MainLayout


@Composable
fun DeepScreen(id: String?) {
    val localContext = LocalContext.current
    val activity = localContext as ComponentActivity
    var input by rememberSaveable { mutableStateOf("") }

    Column {
        Text("Welcome $id")
        TextField(
            value = input,
            onValueChange = {input=it},
            label={ Text("Please enter a value you want to send back") },
            isError=input.isEmpty()
        )

        Button(onClick = {
            val resultIntent = activity.intent
            resultIntent.putExtra("resultData", input) // Set the value to return as a result
            localContext.setResult(Activity.RESULT_OK, resultIntent)
            localContext.finish() // Finish the activity
        }) {
            Text("Send back a value to launching app")
        }
    }



}