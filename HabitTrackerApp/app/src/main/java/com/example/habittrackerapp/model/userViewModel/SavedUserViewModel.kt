package com.example.habittrackerapp.model.userViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavedUserViewModel(private val savedUserRepository: SavedUserRepository) : ViewModel() {
    // private UI state (MutableStateFlow)
    private val _uiState = MutableStateFlow(SavedUser())
    // public getter for the state (StateFlow)
    val uiState: StateFlow<SavedUser> = _uiState.asStateFlow()

    /* Method called when ViewModel is first created */
    init {
        // Start collecting the data from the data store when the ViewModel is created.
        viewModelScope.launch {
            savedUserRepository.getProfile().collect { savedUser ->
                _uiState.value = savedUser
            }
        }
    }

    fun saveEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(email = email, password = password) }
            savedUserRepository.saveProfile(_uiState.value)
        }
    }
}

class SavedUserViewModelSavedFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SavedUserViewModel(MyApp.appModule.savedUserRepository) as T
    }
}