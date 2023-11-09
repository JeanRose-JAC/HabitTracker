package com.example.habittrackerapp.model

import com.example.habittrackerapp.data
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class UserRepositoryFirestore (val db: FirebaseFirestore) : UserDataRepository {

    val dbUser: CollectionReference = db.collection("Profile")
    var UserId = "main-profile"

    override suspend fun saveUser(profileData: User) {
        UserId=profileData.Email
        dbUser.document(UserId).set(profileData)
            .addOnSuccessListener {
                println("Profile saved.")
            }
            .addOnFailureListener { e ->
                println("Error saving profile: $e")
            }

    }

    override suspend fun getUser(): Flow<User> = callbackFlow {
        val docRef = dbUser.document("main-profile")
        val subscription = docRef.addSnapshotListener{ snapshot, error ->
            if (error != null) {
                // An error occurred
                println("Listen failed: $error")
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                // The user document has data
                val user = snapshot.toObject(User::class.java)
                if (user != null) {
                    println("Real-time update to user")
                    trySend(user)
                } else {
                    println("User is / has become null")
                    trySend(User("")) // If there is no saved profile, then send a default object
                }
            } else {
                // The user document does not exist or has no data
                println("User does not exist")
                trySend(User("")) // send default object
            }
        }
        awaitClose { subscription.remove() }
    }

    override suspend fun getUser(userId: String): Flow<User> = callbackFlow {
        val docRef = dbUser.document(userId)
        val subscription = docRef.addSnapshotListener{ snapshot, error ->
            if (error != null) {
                // An error occurred
                println("Listen failed: $error")
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                // The user document has data
                val user = snapshot.toObject(User::class.java)
                if (user != null) {
                    println("Real-time update to user")
                    UserId=user.Email;
                    trySend(user)
                } else {
                    println("User is / has become null")
                    trySend(User("")) // If there is no saved profile, then send a default object
                }
            } else {
                // The user document does not exist or has no data
                println("User does not exist")
                trySend(User("")) // send default object
            }
        }
        awaitClose { subscription.remove() }
    }


    override suspend fun clear() {
        dbUser.document(UserId)
            .delete()
            .addOnSuccessListener { println("User successfully deleted!") }
            .addOnFailureListener { error -> println("Error deleting user: $error") }
    }

}
