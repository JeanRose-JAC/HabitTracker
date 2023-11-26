package com.example.habittrackerapp.model

import kotlinx.coroutines.flow.Flow

interface SavedUserRepository {
    suspend fun saveProfile(user: SavedUser)
    fun getProfile(): Flow<SavedUser>
    suspend fun clear()
}