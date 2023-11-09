package com.example.habittrackerapp.model

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun saveUser(profileData: User)

    suspend fun getUser(): Flow<User> // get the user from it's email

    suspend fun clear()
    
}