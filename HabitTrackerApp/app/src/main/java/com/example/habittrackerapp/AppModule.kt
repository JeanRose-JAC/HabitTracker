package com.example.habittrackerapp

import android.content.Context
import com.example.habittrackerapp.auth.AuthRepository
import com.example.habittrackerapp.auth.AuthRepositoryFirebase
import com.example.habittrackerapp.habit.database.HabitDatabase
import com.example.habittrackerapp.habit.database.OfflineHabitsRepository
import com.example.habittrackerapp.model.HabitRepository
import com.example.habittrackerapp.model.UserDataRepository
import com.example.habittrackerapp.model.UserRepositoryFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/** This module provides the specific object(s) we will inject */
class AppModule(
    private val appContext: Context,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore

) {
    /* Create appropriate repository (backed by a DataStore) on first use.
       Only one copy will be created during lifetime of the application. */
    val profileRepository : UserDataRepository by lazy {
        UserRepositoryFirestore(firestore)
    }

    val authRepository : AuthRepository by lazy {
        AuthRepositoryFirebase(auth) // inject Firebase auth
    }

    val habitsRepository: HabitRepository by lazy {
        OfflineHabitsRepository(HabitDatabase.getDatabase(appContext).habitDao())
    }
}