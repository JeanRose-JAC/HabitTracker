package com.example.habittrackerapp.habit.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable to get user input on habit description
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDescription(desc: String, onDescChange: (String) -> Unit, modifier: Modifier = Modifier) {

    Column {
        Text(
            text = "Description",
            modifier = Modifier.padding(bottom = 5.dp))

        TextField(
            value = desc,
            onValueChange = onDescChange,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )
    }

}