package com.example.habittrackerapp.habit.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittrackerapp.model.Habit


@Composable
fun HabitQuestionnaireScreen(modifier: Modifier = Modifier) {
    var desc by rememberSaveable { mutableStateOf("") }
    var startDate by rememberSaveable { mutableStateOf("") }
    var frequency by rememberSaveable { mutableStateOf("") }
    var type by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "What habit do you want to track?",
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Text(
            text = "*Fill out all information.*",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 12.sp,
        )

        HabitDescription(desc, {desc = it})
        HabitStartDate(startDate, {startDate = it})
        HabitFrequency(frequency, {frequency = it})
        HabitTypeQuestion(type, {type = it})
        SubmitButton(Habit(description = desc, startDate = startDate, frequency = frequency, type = type, streak = 0))
    }
}