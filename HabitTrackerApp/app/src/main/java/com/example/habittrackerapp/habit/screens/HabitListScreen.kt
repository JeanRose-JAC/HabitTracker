package com.example.habittrackerapp.habit.screens

import Routes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.model.habitViewModel.Habit
import com.example.habittrackerapp.model.habitViewModel.HabitCreateAndListViewModel
import com.example.habittrackerapp.model.habitViewModel.HabitViewModelProvider

@Composable
fun HabitListScreen(
    navigateToHabitGet: (Int) -> Unit,
    myViewModel: HabitCreateAndListViewModel = viewModel(factory = HabitViewModelProvider.Factory),
    modifier : Modifier = Modifier) {
    val navController = LocalNavController.current
    val homeUiState by myViewModel.habitListUiState.collectAsState()

    Column {
        Text(
            text = "List of All Habits",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign =  TextAlign.Center
        )

        TextButton(onClick = { navController.navigate(Routes.HabitQuestionnaire.route) }) {
            Text("Add Habit")
        }

        TextButton(onClick = { navController.navigate(Routes.HabitForTodayList.route) }) {
            Text("View List Of Habits For Today")
        }

        ListBody(habitList = homeUiState.habitList, onHabitClick = navigateToHabitGet)

    }
}

@Composable
fun ListBody(
    habitList: List<Habit>, onHabitClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if(habitList.isEmpty()){
            Text("You have no saved habits")
        }
        else{
            HabitList(
                habitList = habitList,
                onHabitClick = { onHabitClick(it.id) },
            )
        }
    }
}

@Composable
private fun HabitList(
    habitList: List<Habit>, onHabitClick: (Habit) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier){
        items(items = habitList, key = {it.id}){ habit ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .clickable() { onHabitClick(habit) }
            ){
                Text(
                    text = habit.description,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

                Text(
                    text = habit.frequency,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 2.dp, bottom = 2.dp)
                )

                Text(
                    text = "Streak: " + habit.streak.toString(),
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Justify,
                    fontSize = 16.sp,
                )
            }
        }
    }

}