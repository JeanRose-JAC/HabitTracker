package com.example.habittrackerapp.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittrackerapp.R

/**
 * Composable for the About screen
 */
@Composable
fun AboutScreen(modifier : Modifier = Modifier) {
    Column (
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Track your daily progress effortlessly with Habit Minder. " +
                    "Build positive habits and break old routines with ease. " +
                    "Stay motivated, set achievable goals, " +
                    "and watch your life transform one habit at a time",
            textAlign = TextAlign.Justify,
            modifier = Modifier.fillMaxWidth(),
            lineHeight = 25.sp)

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = "Getting started is easy, just create an account and enter the habits that " +
                    "you want to keep track. You also have the ability to categorize your habits " +
                    "which makes it easy for you to see which aspects of your life you are actively" +
                    " improving. Whether it's health, fitness, productivity or others, our app is dedicated " +
                    "for you and your well-being",
            textAlign = TextAlign.Justify,
            modifier = Modifier.fillMaxWidth(),
            lineHeight = 25.sp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.End,
        ){
            Image(
                painterResource(id = R.drawable.brain_5983810_1280),
                contentDescription = "Habit Minder",
                modifier = modifier
                    .size(225.dp),
                contentScale = ContentScale.Crop,
            )
        }
    }
}