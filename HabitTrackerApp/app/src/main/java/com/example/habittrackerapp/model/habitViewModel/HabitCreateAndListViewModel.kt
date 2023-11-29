package com.example.habittrackerapp.model.habitViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitCreateAndListViewModel(private val habitsRepository: HabitRepository): ViewModel() {

    var habitUiState by mutableStateOf(HabitUiState())
        private set

    val habitListUiState: StateFlow<HabitListUiState> =
        habitsRepository.getAllHabitsStream().map { HabitListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HabitListUiState()
            )

    fun insertHabit() {
        viewModelScope.launch {
            if(validateInput()){
                habitsRepository.insertHabit(habitUiState.habitDetails.toHabit())
            }
        }
    }

    fun updateUiState(habitDetails: HabitDetails) {
        habitUiState =
            HabitUiState(habitDetails = habitDetails, isEntryValid = validateInput(habitDetails))
    }

    private fun validateInput(uiState: HabitDetails = habitUiState.habitDetails): Boolean {
        return with(uiState) {
            description.isNotEmpty() && startDate.isNotEmpty() && frequency.isNotEmpty() && type.isNotEmpty()
        }
    }

}

data class HabitListUiState(val habitList: List<Habit> = listOf())

data class HabitUiState(
    val habitDetails: HabitDetails = HabitDetails(),
    val isEntryValid: Boolean = false
)

data class HabitDetails(
    val id: Int = 0,
    val description : String = "",
    val startDate : String = "",
    val frequency: String = "",
    val type: String = "",
    val streak: Int = 0
)

fun HabitDetails.toHabit(): Habit = Habit(
    id = id,
    description =  description,
    startDate = startDate,
    frequency = frequency,
    type = type,
    streak = streak,
)

fun Habit.toHabitUiState(isEntryValid: Boolean = false) : HabitUiState = HabitUiState(
    habitDetails = this.toHabitDetails(),
    isEntryValid = isEntryValid

)

fun Habit.toHabitDetails(): HabitDetails = HabitDetails(
    id = id,
    description =  description,
    startDate = startDate,
    frequency = frequency,
    type = type,
    streak = streak,
)