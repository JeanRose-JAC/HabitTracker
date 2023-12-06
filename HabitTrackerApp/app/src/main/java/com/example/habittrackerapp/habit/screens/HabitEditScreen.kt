package com.example.habittrackerapp.habit.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittrackerapp.model.habitViewModel.HabitDetails
import com.example.habittrackerapp.model.habitViewModel.HabitUpdateViewModel
import com.example.habittrackerapp.model.habitViewModel.HabitViewModelProvider

@Composable
fun HabitEditScreen (myViewModel: HabitUpdateViewModel = viewModel(factory = HabitViewModelProvider.Factory))
{
    val item = myViewModel.habitUiState.habitDetails

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "Edit Habit",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign =  TextAlign.Center
        )

        Text(
            text = "*Fill out all information.*",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 10.dp),
            fontSize = 12.sp,
        )
        
        EditForm(habitDetails = item, onValueChange = myViewModel::updateUiState)

    }
}

@Composable
fun EditForm(
    habitDetails: HabitDetails,
    onValueChange: (HabitDetails) -> Unit = {}
    ) {
    HabitDescription(habitDetails.description, {
        if(it.length <= 75){
            onValueChange(habitDetails.copy(description = it))
        }}
    )
    HabitStartDate(habitDetails.startDate, {onValueChange(habitDetails.copy(startDate = it))})
    HabitFrequency(habitDetails.frequency, {onValueChange(habitDetails.copy(frequency = it))})
    HabitTypeQuestion(habitDetails.type, {onValueChange(habitDetails.copy(type = it))})
    SaveChanges()
}