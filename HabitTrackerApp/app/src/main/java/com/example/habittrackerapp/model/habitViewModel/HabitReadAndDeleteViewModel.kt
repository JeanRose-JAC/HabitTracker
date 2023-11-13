package com.example.habittrackerapp.model.habitViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.model.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

object HabitDetailsDestination {
    val route = "habit_details"
    const val habitIdArg = "habitId"
    val routeWithArgs = "$route/{$habitIdArg}"
}

class HabitReadAndDeleteViewModel (savedStateHandle: SavedStateHandle,
                                   private val habitsRepository: HabitRepository
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[HabitDetailsDestination.habitIdArg]) //figure this out

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

    fun increaseStreak(){
        viewModelScope.launch {
            val currentItem = uiState.value.habitDetails.toHabit()
            habitsRepository.updateHabit(currentItem.copy(streak = currentItem.streak + 1))
        }
    }

    suspend fun deleteHabit() {
        habitsRepository.deleteHabit(uiState.value.habitDetails.toHabit())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class HabitDetailsUiState(
    val habitDetails: HabitDetails = HabitDetails()
)