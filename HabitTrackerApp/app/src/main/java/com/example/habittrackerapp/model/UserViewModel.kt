package com.example.habittrackerapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.MyApp
import com.example.habittrackerapp.data
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Simple view model that keeps track of a single value (count in this case) */

class UserViewModel (private val profileRepository: UserDataRepository) : ViewModel() {
    // private UI state (MutableStateFlow)
    private val _uiState = MutableStateFlow(User(""))
    // public getter for the state (StateFlow)
    val uiState: StateFlow<User> = _uiState.asStateFlow()

    /* Method called when ViewModel is first created */
    init {
        // Start collecting the data from the data store when the ViewModel is created.
        viewModelScope.launch {
            profileRepository.getUser().collect { user ->
                _uiState.value = user
            }
        }
    }
//
//    fun setFirstName(newName: String) {
//        viewModelScope.launch {
//            _uiState.update { it.copy(name = newName) }
//            profileRepository.saveProfile(_uiState.value)
//        }
//    }

    fun addUser(user:User) {
        viewModelScope.launch {
            _uiState.value=user
            profileRepository.saveUser(_uiState.value)
        }
    }
    fun getUser(userId:String){
        viewModelScope.launch {

        }
    }




    fun clearProfile() {
        viewModelScope.launch {
            profileRepository.clear()
        }
    }

}

/* ViewModel Factory that will create our view model by injecting the
      ProfileDataStore from the module.
 */
class UserViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(MyApp.appModule.profileRepository) as T
    }
}