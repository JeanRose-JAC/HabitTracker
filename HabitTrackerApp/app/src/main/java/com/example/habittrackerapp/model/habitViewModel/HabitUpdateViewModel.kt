package com.example.habittrackerapp.model.habitViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Habits view model to use when updating an existing habit
 */
class HabitUpdateViewModel (savedStateHandle: SavedStateHandle,
                            private val habitsRepository: HabitRepository
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[Routes.EditHabit.habitIdArg])

    var habitUiState by mutableStateOf(HabitUiState())
        private set

    init {
        viewModelScope.launch {
            habitUiState = habitsRepository.getHabitStream(itemId)
                .filterNotNull()
                .first()
                .toHabitUiState(true)
        }
    }

    fun updateHabit() {
        viewModelScope.launch(){
            if(validateInput(habitUiState.habitDetails)){
                habitsRepository.updateHabit(habitUiState.habitDetails.toHabit())
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

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}