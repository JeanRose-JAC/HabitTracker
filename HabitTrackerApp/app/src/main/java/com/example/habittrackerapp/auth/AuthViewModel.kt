package com.example.habittrackerapp.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.MyApp
import com.example.habittrackerapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll


class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    // Return a StateFlow so that the composable can always update
    // based when the value changes
    fun currentUser(): StateFlow<User?> {
        return authRepository.currentUser()
    }

    fun signUp(email: String,firstname:String,lastname:String,gender:String,profilePicture:String,password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signUp(email,firstname,lastname,gender,profilePicture,password)

        }
    }
    fun signIn(email: String, password: String) {
       viewModelScope.launch(Dispatchers.IO) {
            authRepository.signIn(email, password);
       }
    }
    fun signOut() {
        authRepository.signOut()
    }
    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.delete()
        }
    }
}

/* ViewModel Factory that will create our view model by injecting the
      authRepository from the module.
 */
class AuthViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(MyApp.appModule.authRepository) as T
    }
}