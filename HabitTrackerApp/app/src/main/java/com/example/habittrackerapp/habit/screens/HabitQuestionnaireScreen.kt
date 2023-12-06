package com.example.habittrackerapp.habit.screens

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittrackerapp.model.habitViewModel.Habit

/**
 * Composable to get user input to create a habit
 */
@Composable
fun HabitQuestionnaireScreen(desc2: String? = null, modifier: Modifier = Modifier) {
    val localContext = LocalContext.current
    val activity = localContext as ComponentActivity
    var desc by rememberSaveable { mutableStateOf("") }
    var startDate by rememberSaveable { mutableStateOf("") }
    var frequency by rememberSaveable { mutableStateOf("") }
    var type by rememberSaveable { mutableStateOf("") }

    var fromLauncher = false;

    if(desc2 != null){
        desc = desc2
        fromLauncher = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "What habit do you want to track?",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign =  TextAlign.Center
        )

        Text(
            text = "*Fill out all information.*",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 12.sp,
        )

        HabitDescription(desc, {if(it.length <= 75){
            desc = it
        }})
        HabitStartDate(startDate, {startDate = it})
        HabitFrequency(frequency, {frequency = it})
        HabitTypeQuestion(type, {type = it})
        SubmitButton(Habit(description = desc, startDate = startDate, frequency = frequency, type = type, streak = 0))
        if (desc2 != null) {
            Button(onClick = {
                val resultIntent = activity.intent
                resultIntent.putExtra("resultData", "You are back at the launcher app.") // Set the value to return as a result
                localContext.setResult(Activity.RESULT_OK, resultIntent)
                localContext.finish() // Finish the activity
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text("Go back to launcher app")
            }
        }
    }
}