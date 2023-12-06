package com.example.habittrackerapp.auth

import com.example.habittrackerapp.model.userViewModel.User
import kotlinx.coroutines.flow.StateFlow

// Return a StateFlow so that the composable can always update when
//   the current authorized user status changes for any reason
interface AuthRepository {
    fun currentUser() : StateFlow<User?>

    fun hasCurrentUserDirect() : Boolean

    suspend fun signUp(email: String,password: String): Boolean

    suspend fun signIn(email: String, password: String): Boolean

    fun signOut():Boolean

    suspend fun delete():Boolean
}