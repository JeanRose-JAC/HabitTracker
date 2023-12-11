package com.example.habittrackerapp.habit.screens

import Routes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
 * Composable to display all of the user's habits
 */
@Composable
fun HabitListScreen(
    navigateToHabitGet: (Int) -> Unit,
    myViewModel: HabitCreateAndListViewModel = viewModel(factory = HabitViewModelProvider.Factory),
    modifier : Modifier = Modifier) {
    val navController = LocalNavController.current
    val homeUiState by myViewModel.habitListUiState.collectAsState()
    Image(painter= painterResource(R.drawable.background),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize(),
        alpha = 0.45F)
    Column {
        Text(
            text = "List of All Habits",
            fontSize = 24.sp,
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign =  TextAlign.Center
        )

        Row(  modifier = Modifier
            .align(Alignment.CenterHorizontally)){
            Button(onClick = { navController.navigate(Routes.HabitForTodayList.route) },
                colors =  ButtonDefaults.buttonColors(containerColor =  Color(102,123,104))) {
                Text("View List Of Habits For Today")
            }
            Button(onClick = { navController.navigate(Routes.HabitQuestionnaire.route)},
                colors =  ButtonDefaults.buttonColors(containerColor =  Color(102,123,104)),
                modifier = Modifier
                .padding(start=10.dp)) {
                Text("Add Habit")
            }
        }





        ListBody(habitList = homeUiState.habitList, onHabitClick = navigateToHabitGet)

    }
}

@Composable
fun ListBody(
    habitList: List<Habit>, onHabitClick: (Int) -> Unit, today: Boolean = false ,modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        if(habitList.isEmpty()){
            if(today){
                Text("You have no habits to be done today.",modifier=modifier.fillMaxWidth().padding(start=20.dp,top=20.dp))
            }
            else{
                Text("You have no saved habits.",modifier=modifier.fillMaxWidth().padding(start=65.dp))
            }
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
                    containerColor = Color(	163,184,153)
                ),
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable() { onHabitClick(habit) }
            ){
                    Text(
                        text = habit.description,
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Left,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(20.dp, 0.dp),

                        )
                    Row(modifier = modifier
                        .fillMaxWidth()
                       , horizontalArrangement = Arrangement.Center) {
                        Column{
                            Text(
                                text = habit.frequency,
                                modifier = modifier.padding(10.dp)
                            )
                        }
                        Column(modifier = modifier.padding(80.dp,0.dp)){}
                        Column{
                            Text(
                                text = "Streak: " +habit.streak.toString(),
                                modifier = modifier.padding(10.dp),
                                fontSize = 16.sp,
                            )
                        }

                    }


            }
            Divider(modifier = modifier.fillMaxWidth())//Separates each item
        }
    }

}