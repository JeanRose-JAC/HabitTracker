package com.example.habittrackerapp.habit.screens

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.model.Habit
import com.example.habittrackerapp.model.habitViewModel.HabitCreateAndListViewModel
import com.example.habittrackerapp.model.habitViewModel.HabitViewModelProvider

@Composable
fun HabitsForTodayListScreen(navigateToHabitGet: (Int) -> Unit,
                             myViewModel: HabitCreateAndListViewModel = viewModel(factory = HabitViewModelProvider.Factory),
                             modifier : Modifier = Modifier
) {
    val navController = LocalNavController.current
    val homeUiState by myViewModel.habitListUiState.collectAsState()
    var list = mutableListOf<Habit>()

    Column {
        Text(
            text = "List of Habits For Today",
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

        TextButton(onClick = { navController.navigate(Routes.HabitList.route) }) {
            Text("View List Of All Habits")
        }

        ListBody(habitList = getHabitsForToday(homeUiState.habitList), onHabitClick = navigateToHabitGet, true)

    }
}

fun getHabitsForToday(list: List<Habit>) : List<Habit> {
    var listToday = mutableListOf<Habit>()

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    for(habit in list){
        if(habit.startDate == "$dayOfMonth/${month + 1}/$year"){
            listToday.add(habit)
        }
    }

    return listToday
}