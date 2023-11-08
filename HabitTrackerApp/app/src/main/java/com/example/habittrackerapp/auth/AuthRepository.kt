package com.example.habittrackerapp.auth

import com.example.habittrackerapp.model.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    fun currentUser() : StateFlow<User?>

    suspend fun signUp(email: String,firstname:String,lastname:String,gender:String,profilePicture:String,password: String): Boolean //may have to change that

    suspend fun signIn(email: String, password: String): Boolean

    fun signOut()

    suspend fun delete()
}