package com.example.habittrackerapp.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HabitViewModel (private val habitRepository: HabitRepository) : ViewModel() {
    // private UI state (MutableStateFlow)
    private val _uiState = MutableStateFlow(Habit())
    // public getter for the state (StateFlow)
    val uiState: StateFlow<Habit> = _uiState.asStateFlow()

    init{
        viewModelScope.launch{
            habitRepository.getHabit().collect { habit ->
                 _uiState.value = habit
            }
        }
    }

    fun setDescription(desc: String){
        viewModelScope.launch{
            _uiState.update {it.copy(description = desc) }
            habitRepository.saveHabit(_uiState.value)
        }
    }

    fun setStartDate(date: String){
        viewModelScope.launch{
            _uiState.update {it.copy(startDate = date) }
            habitRepository.saveHabit(_uiState.value)
        }
    }

    fun setFrequency(frequency: String){
        viewModelScope.launch{
            _uiState.update {it.copy(frequency = frequency) }
            habitRepository.saveHabit(_uiState.value)
        }
    }

    fun setType(type: String){
        viewModelScope.launch{
            _uiState.update {it.copy(type = type) }
            habitRepository.saveHabit(_uiState.value)
        }
    }

    fun incrementStreak(){
        viewModelScope.launch {
            val count = _uiState.value.streak
            _uiState.update { currentState ->
                currentState.copy(streak = count + 1)
            }
            habitRepository.saveHabit(_uiState.value)
        }
    }

    fun resetStreak(){
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(streak = 0)
            }
            habitRepository.saveHabit(_uiState.value)
        }
    }
}