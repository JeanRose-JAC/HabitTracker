package com.example.habittrackerapp.model.userViewModel

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun saveUser(oldName: String, profileData: User)

    suspend fun getUsers(): Flow<List<User>>
    suspend fun getUser(userId:String): Flow<User> // get the user from it's email

    suspend fun delete(name: String)
    
}