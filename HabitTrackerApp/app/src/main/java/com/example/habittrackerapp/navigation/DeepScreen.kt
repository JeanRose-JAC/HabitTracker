package com.example.habittrackerapp.navigation


import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.habittrackerapp.layout.MainLayout


@Composable
fun DeepScreen(id: String?) {
    val localContext = LocalContext.current
    val activity = localContext as ComponentActivity
    var input by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Welcome $id", modifier=Modifier.padding(20.dp))
        TextField(
            value = input,
            onValueChange = {input=it},
            label={ Text("Please enter to change your name") },
            isError=input.isEmpty(),
            modifier=Modifier.padding(20.dp)
        )

        Button(onClick = {
            val resultIntent = activity.intent
            resultIntent.putExtra("resultData", input) // Set the value to return as a result
            localContext.setResult(Activity.RESULT_OK, resultIntent)
            localContext.finish() // Finish the activity
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(148,156,241)), modifier=Modifier.padding(20.dp)) {
            Text("Send back a value to launching app")
        }
    }



}