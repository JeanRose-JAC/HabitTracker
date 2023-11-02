package com.example.habittrackerapp.navigation

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.habittrackerapp.layout.MainLayout

@Composable
fun DeepScreen(id: String?) {
    val localContext = LocalContext.current
    val activity = localContext as ComponentActivity

        Text("Welcome $id")

        Button(onClick = {
            val resultIntent = activity.intent
            resultIntent.putExtra("resultData", "This is my result") // Set the value to return as a result
            localContext.setResult(Activity.RESULT_OK, resultIntent)
            localContext.finish() // Finish the activity
        }) {
            Text("Send back a value to launching app")
    }
}
