package com.example.habittrackerapp.habit.screens

import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalHabitList
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.habit.HabitSavedFactory
import com.example.habittrackerapp.habit.HabitViewModel

@Composable
fun HabitListScreen(
    myViewModel: HabitViewModel = viewModel(factory= HabitSavedFactory()),
    modifier : Modifier = Modifier) {
    val navController = LocalNavController.current
    val habitList = LocalHabitList.current

    LazyColumn {
        item{
            Text(
                text = "List of All Habits",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium,
                textAlign =  TextAlign.Center
            )
        }

        items(habitList){ habit ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary
                ),
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text = habit.description,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

                Text(
                    text = habit.frequency,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 2.dp, bottom = 2.dp)
                )

                Text(
                    text = "Streak: " + habit.streak.toString(),
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Justify,
                    fontSize = 16.sp
                )

                Row (
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp),
                ){
                    TextButton(onClick = {navController.navigate(Routes.HabitItem.go(habit.id.toString()))}){
                        Text("View")
                    }

                    TextButton(onClick = {habitList.remove(habit)}){
                        Text("Remove")
                    }

                }
            }
        }

    }
}