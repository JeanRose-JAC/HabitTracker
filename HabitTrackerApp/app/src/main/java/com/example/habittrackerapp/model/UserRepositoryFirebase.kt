package com.example.habittrackerapp.model

import com.example.habittrackerapp.auth.AuthRepositoryFirebase
import com.example.habittrackerapp.user.User
import kotlinx.coroutines.flow.Flow

class UserRepositoryFirebase (val authRepository: AuthRepositoryFirebase) : UserRepository {

    override suspend fun saveProfile(profileData: User) {
        TODO("Not yet implemented")
    }

    override fun getProfile(): Flow<User> {
        // Access a Cloud Firestore instance from your Activity

        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }

}