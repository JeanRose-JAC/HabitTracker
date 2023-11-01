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

@Composable
fun SaveChanges (habit: Habit,
                 changes: List<String>,
                 myViewModel: HabitViewModel = viewModel(factory= HabitSavedFactory()),
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