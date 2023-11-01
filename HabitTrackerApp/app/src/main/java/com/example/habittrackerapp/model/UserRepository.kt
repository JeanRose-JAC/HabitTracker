package com.example.habittrackerapp.model

import com.example.habittrackerapp.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveProfile(profileData: User)
    fun getProfile(): Flow<User>
    suspend fun clear()
}