package com.example.habittrackerapp.model.habitViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habittrackerapp.MyApp

/**
 * Initializes all the habit view model
 */
object HabitViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HabitCreateAndListViewModel(
                MyApp.appModule.habitsRepository
            )
        }

        initializer {
            HabitReadAndDeleteViewModel(
                this.createSavedStateHandle(),
                MyApp.appModule.habitsRepository
            )
        }

        initializer {
            HabitUpdateViewModel(
                this.createSavedStateHandle(),
                MyApp.appModule.habitsRepository
            )
        }
    }
}

fun CreationExtras.myApp(): MyApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApp)