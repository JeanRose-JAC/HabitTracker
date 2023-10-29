package com.example.habittrackerapp.habit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.LocalHabitList
import com.example.habittrackerapp.LocalNavController
import com.example.habittrackerapp.habit.Habit
import com.example.habittrackerapp.habit.HabitSavedFactory
import com.example.habittrackerapp.habit.HabitViewModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Composable
fun HabitItemScreen(idString: String,
                    myViewModel: HabitViewModel = viewModel(factory= HabitSavedFactory()),
                    modifier: Modifier = Modifier
){
    val navController = LocalNavController.current
    val habitList = LocalHabitList.current
    val id = UUID.fromString(idString)
    val item : Habit = habitList.first { it.id == id }
    val type = fromStringToHabitType(item.type)

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
            onClick = {navController.navigate(Routes.HabitList.route)},
            modifier = Modifier.padding(16.dp)
        ){
            Text("View all habits")
        }
    }

}
