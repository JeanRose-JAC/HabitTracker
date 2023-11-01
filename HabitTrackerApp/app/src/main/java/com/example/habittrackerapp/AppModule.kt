package com.example.habittrackerapp

import android.content.Context
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.example.habittrackerapp.auth.AuthRepository
import com.example.habittrackerapp.auth.AuthRepositoryFirebase

/** This module provides the specific object(s) we will inject */
class AppModule(
    private val appContext: Context
) {
    /* Create appropriate repository (backed by a DataStore) on first use.
       Only one copy will be created during lifetime of the application. */
//    val profileRepository : ProfileRepository by lazy {
//        ProfileRepositoryDataStore(appContext)
//    }
    val authRepository : AuthRepository by lazy {
        AuthRepositoryFirebase(Firebase.auth) // inject Firebase auth
    }
}