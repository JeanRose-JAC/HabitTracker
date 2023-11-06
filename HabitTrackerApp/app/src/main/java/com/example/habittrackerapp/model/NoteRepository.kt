package com.example.habittrackerapp.model

import com.example.habittrackerapp.user.User
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun saveUser(oldName: String, profileData: User)

    suspend fun getUser(email:String): Flow<User>

    suspend fun getUsers(): Flow<List<User>>
    suspend fun delete(email:String)
}