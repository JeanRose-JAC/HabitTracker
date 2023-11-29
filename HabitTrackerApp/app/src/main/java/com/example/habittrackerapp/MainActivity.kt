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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.habittrackerapp.layout.MainLayout
import com.example.habittrackerapp.signInSignUp.rememberMutableStateListOf
import com.example.habittrackerapp.model.noteViewModel.Note
import com.example.habittrackerapp.ui.theme.HabitTrackerAppTheme
import com.example.habittrackerapp.model.userViewModel.User
import com.google.firebase.FirebaseApp

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found!") }
val data= compositionLocalOf<User>{ error("No User found!")}
val LocalNotesList = compositionLocalOf<SnapshotStateList<Note>> { error("No notes found!") }

//Habit Room Database is implemented based on the "Persist data with Room" codelab
//Source: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#0

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            
            HabitTrackerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    val userInput by rememberSaveable { mutableStateOf(User("")) }


                    val notesList = rememberMutableStateListOf<Note>()

                    CompositionLocalProvider(LocalNavController provides navController, data provides userInput, LocalNotesList provides notesList) {
                        MainLayout("Habit Minder") {
                            Router()
                        }

                    }
                }
            }
        }
    }
}
