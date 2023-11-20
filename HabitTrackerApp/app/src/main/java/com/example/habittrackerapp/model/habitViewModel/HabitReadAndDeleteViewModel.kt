package com.example.habittrackerapp.model.habitViewModel

import android.icu.util.Calendar
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.habit.Frequency
import com.example.habittrackerapp.model.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class HabitReadAndDeleteViewModel (savedStateHandle: SavedStateHandle,
                                   private val habitsRepository: HabitRepository
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[Routes.HabitItem.habitIdArg])
    private val calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH)
    private val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    private val today = "$dayOfMonth/${month + 1}/$year"

    val uiState: StateFlow<HabitDetailsUiState> =
        habitsRepository.getHabitStream(itemId)
            .filterNotNull()
            .map {
                HabitDetailsUiState(habitDetails = it.toHabitDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HabitDetailsUiState()
            )

    fun increaseStreak() {
        viewModelScope.launch {
            val currentItem = uiState.value.habitDetails.toHabit()
            val nextDate = Calendar.getInstance()
            nextDate.time = SimpleDateFormat("dd/MM/yyyy").parse(today)

            println("Original Date: $nextDate")

            val nextCalendar = calculateNextDate(nextDate, currentItem.frequency)
            val nextYear = nextCalendar.get(Calendar.YEAR)
            val nextMonth = nextCalendar.get(Calendar.MONTH) + 1 // Adjusting the month to be one-based
            val nextDayOfMonth = nextCalendar.get(Calendar.DAY_OF_MONTH)
            val nextDateString = "$nextDayOfMonth/$nextMonth/$nextYear"

            habitsRepository.updateHabit(currentItem.copy(streak = currentItem.streak + 1, startDate = nextDateString))
        }
    }

    private fun calculateNextDate(currentDate: Calendar, frequency: String): Calendar {
        when (frequency) {
            Frequency.Daily.toString() -> currentDate.add(Calendar.DAY_OF_MONTH, 1)
            Frequency.Weekly.toString() -> currentDate.add(Calendar.DAY_OF_MONTH, 7)
            Frequency.Monthly.toString() -> currentDate.add(Calendar.MONTH, 1)
        }
        return currentDate
    }

    fun resetStreak() {
        viewModelScope.launch {
            val currentItem = uiState.value.habitDetails.toHabit()
            habitsRepository.updateHabit(currentItem.copy(streak = 0, startDate = today))
        }
    }

    fun deleteHabit() {
        viewModelScope.launch {
            habitsRepository.deleteHabit(uiState.value.habitDetails.toHabit())
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class HabitDetailsUiState(
    val habitDetails: HabitDetails = HabitDetails()
)