package com.example.habittrackerapp.model.userViewModel

import kotlinx.coroutines.flow.Flow

/**
 * This interface is used to get, save or clear a profile off the phone
 */
interface SavedUserRepository {
    suspend fun saveProfile(user: SavedUser)
    fun getProfile(): Flow<SavedUser>
    suspend fun clear()
}