package com.example.habittrackerapp.habit.screens

import android.icu.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.R
import com.example.habittrackerapp.model.habitViewModel.Habit
import com.example.habittrackerapp.model.habitViewModel.HabitCreateAndListViewModel
import com.example.habittrackerapp.model.habitViewModel.HabitViewModelProvider

/**
 * Composable to display the habits due for the day
 */
@Composable
fun HabitsForTodayListScreen(navigateToHabitGet: (Int) -> Unit,
                             myViewModel: HabitCreateAndListViewModel = viewModel(factory = HabitViewModelProvider.Factory),
) {
    val navController = LocalNavController.current
    val homeUiState by myViewModel.habitListUiState.collectAsState()
    var list = mutableListOf<Habit>()
    Image(painter= painterResource(R.drawable.background),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize(),
        alpha = 0.45F)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "List of Habits For Today",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign =  TextAlign.Center
        )

        Row(  modifier = Modifier
            .align(Alignment.CenterHorizontally)){
            Button(
                onClick = { navController.navigate(Routes.HabitList.route)},
                colors =  ButtonDefaults.buttonColors(containerColor =  Color(102,123,104))
                ,modifier = Modifier.padding(end=10.dp)) {
                Text("View List Of All Habits",modifier = Modifier
                    .padding(20.dp,0.dp))
            }
            Button(
                onClick = { navController.navigate(Routes.HabitQuestionnaire.route)},
                colors =  ButtonDefaults.buttonColors(containerColor =  Color(102,123,104)),
                modifier = Modifier.padding()) {
                Text("Add Habit")
            }
        }

        ListBody(habitList = getHabitsForToday(homeUiState.habitList), onHabitClick = navigateToHabitGet, true)

    }
}

fun getHabitsForToday(list: List<Habit>) : List<Habit> {
    val listToday = mutableListOf<Habit>()

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