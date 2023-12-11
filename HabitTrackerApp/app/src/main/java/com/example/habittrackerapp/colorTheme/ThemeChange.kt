package com.example.habittrackerapp.colorTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


/**
 * A composable that checks if the user wants to switch the color mode of the app*/
@Composable
fun ThemeSwitcher(
    darkTheme: Boolean,
    onThemeChange: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth().background(Color(153,158,135)),
        contentAlignment = Alignment.Center
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                modifier = Modifier.padding(12.dp,0.dp),
                text = if (darkTheme) "Dark Theme" else "Light Theme",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Switch(
                checked = darkTheme,
                onCheckedChange = { onThemeChange() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.DarkGray,
                    uncheckedThumbColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}