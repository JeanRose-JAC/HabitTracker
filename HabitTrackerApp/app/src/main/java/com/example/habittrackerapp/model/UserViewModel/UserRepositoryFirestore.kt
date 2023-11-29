package com.example.habittrackerapp.model.UserViewModel

import com.example.habittrackerapp.auth.AuthRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class UserRepositoryFirestore (val auth: AuthRepository, val db: FirebaseFirestore) :
    UserDataRepository {

    val dbUser: CollectionReference = db.collection("Profile")

    override suspend fun saveUser(oldName: String, profileData: User) {
        dbUser.document(oldName).set(profileData)
            .addOnSuccessListener {
                println("Profile saved.")
            }
            .addOnFailureListener { e ->
                println("Error saving profile: $e")
            }
    }

    override suspend fun getUser(name: String): Flow<User> = callbackFlow {
        val docRef = dbUser.document(name)
        val subscription = docRef.addSnapshotListener{ snapshot, error ->
            if (error != null) {
                // An error occurred
                println("Listen failed: $error")
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                // The user document has data
                val profile = snapshot.toObject(User::class.java)
                if (profile != null) {
                    println("Real-time update to profile")
                    trySend(profile)
                } else {
                    println("Profile is / has become null")
                    trySend(User()) // If there is no saved profile, then send a default object
                }
            } else {
                // The user document does not exist or has no data
                println("Profile does not exist")
                trySend(User()) // send default object
            }
        }
        awaitClose { subscription.remove() }
    }


    override suspend fun delete(name:String) {
        if (auth.hasCurrentUserDirect()) {
            dbUser.document(name)
                .delete()
                .addOnSuccessListener { println("Profile $name successfully deleted!") }
                .addOnFailureListener { error -> println("Error deleting profile $name: $error") }
        } else {
            println("Delete failed: User is not authenticated")
        }
    }

    override suspend fun getUsers(): Flow<List<User>> = callbackFlow {

        // Listen for changes on entire collection
        val subscription = dbUser.addSnapshotListener{ snapshot, error ->
            if (error != null) {
                // An error occurred
                println("Listen failed: $error")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                // The collection has documents, so convert them all to ProfileData objects
                val profiles = snapshot.toObjects(User::class.java)
                if (profiles != null) {
                    println("Real-time update to profile")
                    trySend(profiles)
                } else {
                    println("Profiles has become null")
                    trySend(listOf<User>()) // If there is no saved profile, then send a default object
                }
            } else {
                // The user document does not exist or has no data
                println("Profiles collection does not exist")
                trySend(listOf<User>()) // send default object
            }
        }
        awaitClose { subscription.remove() }
    }

}
