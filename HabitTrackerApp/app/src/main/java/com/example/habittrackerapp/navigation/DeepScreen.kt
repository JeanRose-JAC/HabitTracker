package com.example.habittrackerapp.navigation


import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
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