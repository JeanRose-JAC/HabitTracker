package com.example.habittrackerapp.auth

import com.example.habittrackerapp.model.userViewModel.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    fun currentUser() : StateFlow<User?>

    fun hasCurrentUserDirect() : Boolean

    suspend fun signUp(email: String,password: String): Boolean //may have to change that

    suspend fun signIn(email: String, password: String): Boolean

    fun signOut():Boolean

    suspend fun delete():Boolean
}