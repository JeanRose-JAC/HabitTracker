package com.example.habittrackerapp.model.userViewModel

import com.example.habittrackerapp.model.SavedUser
import kotlinx.coroutines.flow.Flow

interface SavedUserRepository {
    suspend fun saveProfile(user: SavedUser)
    fun getProfile(): Flow<SavedUser>
    suspend fun clear()
}