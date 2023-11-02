package com.example.habittrackerapp.habit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.habittrackerapp.habit.HabitType

/**
 * Composable for the habit type question.
 * Includes a drop down menu with all the choices. The image in the bottom
 * changes depending on the type.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTypeQuestion(type: String, onTypeChange: (String) -> Unit, modifier: Modifier = Modifier) {
    var expandMenu by rememberSaveable { mutableStateOf(false) }
    var img by rememberSaveable { mutableIntStateOf(0) }
    val choices = listOf(
        HabitType.Other.name,
        HabitType.Chores.name,
        HabitType.Health.name,
        HabitType.Fitness.name,
        HabitType.Reading.name,
        HabitType.Sleep.name,
        HabitType.Organization.name)

    Column{
        Text(
            text = "Type",
            modifier = Modifier.padding(bottom = 5.dp))

        TextField(
            value = type,
            onValueChange = onTypeChange,
            trailingIcon = {
                Icon(
                    Icons.Filled.KeyboardArrowDown,"Type picker",
                    Modifier.clickable { expandMenu = !expandMenu })
            },
            modifier = Modifier
                .padding(bottom = 10.dp)
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
                        onTypeChange(label)
                        expandMenu = false
                    }
                )
            }
        }

        //Get image id
        img = fromStringToHabitType(type).id

        //Show image
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painterResource(id = img),
                contentDescription = type,
                modifier = modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
            )
        }

    }

}

/**
 * Convert the string to a Habit Type
 */
fun fromStringToHabitType(type : String) : HabitType {
    return when (type){
        HabitType.Chores.name -> HabitType.Chores
        HabitType.Health.name -> HabitType.Health
        HabitType.Fitness.name -> HabitType.Fitness
        HabitType.Reading.name -> HabitType.Reading
        HabitType.Sleep.name-> HabitType.Sleep
        HabitType.Organization.name -> HabitType.Organization
        else -> {
            HabitType.Other}
    }
}