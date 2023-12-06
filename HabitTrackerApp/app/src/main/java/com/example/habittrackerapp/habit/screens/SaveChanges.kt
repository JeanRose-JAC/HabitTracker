package com.example.habittrackerapp.habit.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.model.habitViewModel.HabitUpdateViewModel
import com.example.habittrackerapp.model.habitViewModel.HabitViewModelProvider

/**
 * Composable for the save changes button when editing a habit
 */
@Composable
fun SaveChanges (
                 myViewModel: HabitUpdateViewModel = viewModel(factory = HabitViewModelProvider.Factory),
                 modifier : Modifier = Modifier
) {
    val navController = LocalNavController.current

    Button(onClick = {
        myViewModel.updateHabit()
        navController.popBackStack()
    },
        modifier = Modifier.padding(16.dp),
        enabled = myViewModel.habitUiState.isEntryValid
    ) {
        Text("Done")
    }
}