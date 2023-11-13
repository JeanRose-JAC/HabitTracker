package com.example.habittrackerapp.model.habitViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.model.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

object HabitEditDestination {
    const val route = "edit_details"
    const val habitIdArg = "habitId"
    val routeWithArgs = "$route/{$habitIdArg}"
}
class HabitUpdateViewModel (savedStateHandle: SavedStateHandle,
                            private val habitsRepository: HabitRepository
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[HabitEditDestination.habitIdArg])

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