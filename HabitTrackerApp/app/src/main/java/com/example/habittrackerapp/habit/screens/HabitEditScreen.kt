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
import com.example.habittrackerapp.LocalHabitList
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.habit.Habit
import java.util.UUID

@Composable
fun HabitEditScreen (idString: String, modifier: Modifier = Modifier){
    val navController = LocalNavController.current
    val habitList = LocalHabitList.current
    val id = UUID.fromString(idString)
    val item : Habit = habitList.first { it.id == id }

    var desc by rememberSaveable { mutableStateOf(item.description) }
    var startDate by rememberSaveable { mutableStateOf(item.startDate) }
    var frequency by rememberSaveable { mutableStateOf(item.frequency) }
    var type by rememberSaveable { mutableStateOf(item.type) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "Edit Habit",
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
        SaveChanges(habit = item, changes = listOf(desc, startDate, frequency, type))
    }
}