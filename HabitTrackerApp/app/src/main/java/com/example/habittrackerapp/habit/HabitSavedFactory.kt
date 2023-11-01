package com.example.habittrackerapp.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittrackerapp.MyApp

class HabitSavedFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HabitViewModel(MyApp.appModule.habitRepository) as T
    }
}