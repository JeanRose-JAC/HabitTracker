package com.example.habittrackerapp.model

import com.example.habittrackerapp.user.User
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun saveUser(profileData: User)

    suspend fun getUser(): Flow<User>

    suspend fun clear()
}