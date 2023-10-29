package com.example.habittrackerapp.habit.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.habit.Frequency

/**
 * Composable for the frequency question.
 * Includes a drop down menu for all the possible choices.
 * Source: https://www.geeksforgeeks.org/drop-down-menu-in-android-using-jetpack-co 
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitFrequency(frequency: String, onFrequencyChange: (String) -> Unit, modifier: Modifier = Modifier){
    //This variable determines whether the menu will be expanded or not
    var expandMenu by rememberSaveable { mutableStateOf(false) }
    val choices = listOf(Frequency.Daily.toString(), Frequency.Weekly.toString(), Frequency.Monthly.toString())

    Column{
        Text(
            text = "Frequency",
            modifier = Modifier.padding(bottom = 5.dp))

        TextField(
            value = frequency,
            onValueChange = onFrequencyChange,
            trailingIcon = {
                //The user can view the menu when they click this icon
                Icon(
                    Icons.Filled.KeyboardArrowDown,"Frequency picker",
                    Modifier.clickable { expandMenu = !expandMenu })
            },
            modifier = Modifier.padding(bottom = 10.dp)
                .fillMaxWidth(),
            readOnly = true,
            )

        DropdownMenu(
            expanded = expandMenu,
            onDismissRequest = { expandMenu = false }
        ) {
            choices.forEach{label ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onFrequencyChange(label)
                        expandMenu = false
                    }
                )
            }
        }

    }

}