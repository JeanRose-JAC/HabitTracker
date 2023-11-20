package com.example.habittrackerapp.Settings

import android.graphics.Paint.Align
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PolicyScreen(modifier: Modifier = Modifier){

    val scrollState = rememberScrollState();

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.verticalScroll(scrollState)) {
        Text("Habit Minder App Policy Guidelines", fontWeight = FontWeight.Bold, modifier = modifier.padding(5.dp))
        Text("1. Overview",fontWeight = FontWeight.Bold, modifier = modifier.padding(5.dp))
        Text("1.1 Purpose: These guidelines outline how Habit Minder collects, " +
                "uses, and protects user data and establish behavior standards within the app.", modifier = modifier.padding(2.dp))
        Text("2. Data Handling",fontWeight = FontWeight.Bold, modifier = modifier.padding(5.dp))
        Text("2.1 Personal Info: Habit Minder may collect user data for personalization, handled per our Privacy Policy.", modifier = modifier.padding(2.dp))
        Text("2.2 Usage Data: Anonymous usage data may be collected to improve app performance.", modifier = modifier.padding(2.dp))
        Text("3. User Privacy",fontWeight = FontWeight.Bold, modifier = modifier.padding(5.dp))
        Text("3.1 Security Measures: Industry-standard security measures protect user data.", modifier = modifier.padding(2.dp))
        Text("3.2 Third-Party Services: Integration with third-party services follows clear data-sharing practices.", modifier = modifier.padding(2.dp))
        Text("4. User Behavior",fontWeight = FontWeight.Bold, modifier = modifier.padding(5.dp))
        Text("4.1 Respectful Interaction: Users must engage respectfully; harassment or abuse is not tolerated.", modifier = modifier.padding(2.dp))
        Text("4.2 Accuracy: Users are responsible for providing accurate information.", modifier = modifier.padding(2.dp))
        Text("5. User Consent and Communication",fontWeight = FontWeight.Bold, modifier = modifier.padding(5.dp))
        Text("5.1 Consent: Use of Habit Minder implies consent to these guidelines and our Privacy Policy", modifier = modifier.padding(2.dp))
        Text("5.2 Communication: Users can manage communication preferences in app settings.", modifier = modifier.padding(2.dp))
        Text("6. Updates",fontWeight = FontWeight.Bold, modifier = modifier.padding(5.dp))
        Text("7. Contact Information",fontWeight = FontWeight.Bold, modifier = modifier.padding(5.dp))
        Text("7.1 Support: Contact Habit Minder support for inquiries.")


    }
}