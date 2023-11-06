package com.example.habittrackerapp

import android.content.Context
import com.example.habittrackerapp.auth.AuthRepository
import com.example.habittrackerapp.auth.AuthRepositoryFirebase
import com.example.habittrackerapp.model.UserRepositoryFirestore
import com.example.habittrackerapp.model.UserRepository
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
    val profileRepository : UserRepository by lazy {
        UserRepositoryFirestore(firestore)
    }

    val authRepository : AuthRepository by lazy {
        AuthRepositoryFirebase(auth) // inject Firebase auth
    }

}