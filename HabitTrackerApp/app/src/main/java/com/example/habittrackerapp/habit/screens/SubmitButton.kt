package com.example.habittrackerapp.habit.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalHabitList
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.habit.Habit
import com.example.habittrackerapp.habit.HabitSavedFactory
import com.example.habittrackerapp.habit.HabitViewModel

/**
 * Composable for the submit button.
 * All fields must be filled before submitting.
 * Go to item display after submission.
 */
@Composable
fun SubmitButton (habit: Habit,
                 myViewModel: HabitViewModel = viewModel(factory=HabitSavedFactory()),
                 modifier : Modifier = Modifier) {
    val navController = LocalNavController.current
    val habitList = LocalHabitList.current

    Button(onClick = {
        if(habit.description.isNotEmpty() &&
            habit.startDate.isNotEmpty() &&
            habit.frequency.isNotEmpty() &&
            habit.type.isNotEmpty())
        {
            habitList.add(habit)
            navController.navigate(Routes.HabitItem.go(habit.id.toString()))
        }
                     },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Done")
    }
}