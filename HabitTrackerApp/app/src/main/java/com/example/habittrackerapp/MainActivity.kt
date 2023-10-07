package com.example.habittrackerapp

import Router
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.habittrackerapp.layout.MainLayout
import com.example.habittrackerapp.navigation.rememberMutableStateListOf
import com.example.habittrackerapp.ui.theme.HabitTrackerAppTheme

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found!") }
val data= compositionLocalOf<SnapshotStateList<String>>{ error("No list found!")}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabitTrackerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val userInput = rememberMutableStateListOf<String>("","","","","","","")

                    CompositionLocalProvider(LocalNavController provides navController, data provides userInput) {
                        MainLayout("Habit Minder") {
                            Router()
                        }

                    }
                }
            }
        }
    }
}
