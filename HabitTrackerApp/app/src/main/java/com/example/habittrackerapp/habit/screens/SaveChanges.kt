package com.example.habittrackerapp.habit.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.model.Habit


@Composable
fun SaveChanges (habit: Habit,
                 changes: List<String>,
                 modifier : Modifier = Modifier
) {
    val navController = LocalNavController.current

    Button(onClick = {
        habit.description = changes[0]
        habit.startDate = changes[1]
        habit.frequency = changes[2]
        habit.type = changes[3]
        navController.navigate(Routes.HabitItem.go(habit.id.toString()))

    },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Done")
    }
}