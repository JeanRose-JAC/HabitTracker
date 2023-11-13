package com.example.habittrackerapp.habit.screens

import Routes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalHabitList
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.model.Habit
import com.example.habittrackerapp.model.habitViewModel.HabitCreateAndListViewModel
import com.example.habittrackerapp.model.habitViewModel.HabitReadAndDeleteViewModel
import com.example.habittrackerapp.model.habitViewModel.HabitViewModelProvider
import com.example.habittrackerapp.model.habitViewModel.toHabit
import java.util.UUID

@Composable
fun HabitItemScreen(
    navigateToEditItem: (Int) -> Unit,
    myViewModel: HabitReadAndDeleteViewModel = viewModel(factory = HabitViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current
    val habit = myViewModel.uiState.collectAsState()
    val item = habit.value.habitDetails.toHabit()
    val type = fromStringToHabitType(item.type)

    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ){
        Image(
            painterResource(id = type.id),
            contentDescription = type.name,
            modifier = modifier
                .size(225.dp),
            contentScale = ContentScale.Crop,
        )

        Card (
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ){
            Text(
                text = item.description,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Justify,
                fontSize = 20.sp
            )
            Text(
                text = "Start date: " + item.startDate,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Justify,
                fontSize = 16.sp
            )
            Text(
                text = "Frequency: " + item.frequency,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Justify,
                fontSize = 16.sp
            )
            Text(
                text = "Type: " + item.type,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Justify,
                fontSize = 16.sp
            )
            Text(
                text = "Streak: " + item.streak.toString(),
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Justify,
                fontSize = 16.sp
            )
        }

        Button(
            onClick = {myViewModel.increaseStreak()},
            modifier = Modifier.padding(16.dp)
        ){
            Text("Add 1 streak")
        }

        Button(
            onClick = {navigateToEditItem(habit.value.habitDetails.id)},
            modifier = Modifier.padding(16.dp)
        ){
            Text("Edit Habit")
        }

        Button(
            onClick = {deleteConfirmationRequired = true},
            modifier = Modifier.padding(16.dp)
        ){
            Text("Delete Habit")
        }

        Button(
            onClick = {navController.navigate(Routes.HabitList.route)},
            modifier = Modifier.padding(16.dp)
        ){
            Text("View all habits")
        }

        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    myViewModel.deleteHabit()
                    navController.popBackStack()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
            )
        }

    }

}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Delete Habit") },
        text = { Text("Are you sure you want to delete this habit?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("No")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        })
}
