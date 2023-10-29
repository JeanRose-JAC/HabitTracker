package com.example.habittrackerapp.habit.screens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Composable for the start date question.
 * Includes a date picker for the user to choose the date
 * Source: https://medium.com/@daniel.atitienei/date-and-time-pickers-in-jetpack-compose-f641b1d72dd5
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitStartDate(startDate: String, onStartDateChange: (String) -> Unit, modifier : Modifier = Modifier){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    //For setting up the initial date
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    //Dialog that allows that user to pick a date and save it
    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            onStartDateChange("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
        }, year, month, dayOfMonth
    )

    //Disallow past dates
    datePicker.datePicker.minDate = calendar.timeInMillis

    Column{
        Text(
            text = "Start Date",
            modifier = Modifier.padding(bottom = 5.dp))

        TextField(
            value = startDate,
            onValueChange = onStartDateChange,
            readOnly = true,
            trailingIcon = {
                //Click this icon to bring up the date picker
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    "Date picker",
                    Modifier.clickable { datePicker.show() })
            },
            modifier = Modifier.padding(bottom = 10.dp)
                .fillMaxWidth()
            ,
        )

    }
}