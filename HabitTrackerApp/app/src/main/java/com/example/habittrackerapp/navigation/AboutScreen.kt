package com.example.habittrackerapp.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittrackerapp.R

/**
 * Composable for the About screen
 */
@Composable
fun AboutTheAppScreen(modifier : Modifier = Modifier) {

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



@Composable
fun AboutUsScreen(){

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState()))
    {
        Text(
            text = "About Us",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Our goal is to develop a distinctive habit tracker application featuring" +
                    " a unique notepad function. We believe this innovative tool will empower" +
                    " users to regain control and gain valuable insights into the habits they" +
                    " incorporate into their lives.",
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            lineHeight = 25.sp
        )

        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            "Meet our dedicated team:",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp,0.dp)

        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                "Together, we're committed to providing an intuitive and effective tool" +
                        " for cultivating positive habits. Join us on this journey towards " +
                        "a more controlled and insightful way of living!", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(163, 184, 153))
        ) {
            Column() {
                Text(
                    "Cindy",
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
                Text(
                    text = "Transforming ideas into reality, Cindy utilizes her technical skills to create " +
                            "robust, scalable, and efficient software. Her work ensures a seamless and " +
                            "reliable experience for our users.",
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 20.dp, 20.dp),
                )
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(221, 230, 213))
        ) {
            Column() {
                Text(
                    "Jean Rose",
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
                Text(
                    text = "Jean Rose is our visionary, ensuring that the app continually aligns with " +
                            "users' needs and remains at the forefront of features and innovation.",
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 20.dp, 20.dp)
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(163, 184, 153))
        ) {
            Column() {
                Text(
                    "Anjeli",
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
                Text(
                    text = "With a sharp eye for user experience, Anjeli designs visually appealing and " +
                            "user-friendly interfaces. Prioritizing simplicity, her designs allow " +
                            "users to effortlessly navigate the app while staying engaged",
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 20.dp, 20.dp)
                )
            }

        }
        Spacer(modifier = Modifier.padding(16.dp))
    }
}
