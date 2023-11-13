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
import com.example.habittrackerapp.model.Habit
import com.example.habittrackerapp.model.habitViewModel.HabitCreateAndListViewModel
import com.example.habittrackerapp.model.habitViewModel.HabitViewModelProvider
import com.example.habittrackerapp.model.habitViewModel.toHabitDetails


/**
 * Composable for the submit button.
 * All fields must be filled before submitting.
 * Go to item display after submission.
 */
@Composable
fun SubmitButton (habit: Habit,
                  myViewModel: HabitCreateAndListViewModel = viewModel(factory = HabitViewModelProvider.Factory),
                  modifier : Modifier = Modifier) {
    val navController = LocalNavController.current
    myViewModel.updateUiState(habit.toHabitDetails())

    Button(onClick = {
             myViewModel.insertHabit()
             navController.navigateUp()
                     },
        modifier = Modifier.padding(16.dp),
        enabled = myViewModel.habitUiState.isEntryValid
    ) {
        Text("Done")
    }
}