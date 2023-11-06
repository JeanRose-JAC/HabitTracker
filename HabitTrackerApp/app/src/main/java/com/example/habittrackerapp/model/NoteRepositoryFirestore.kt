package com.example.habittrackerapp.model

import com.example.habittrackerapp.user.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NoteRepositoryFirestore (val db: FirebaseFirestore) : NoteRepository {
    val dbUserProfiles: CollectionReference = db.collection("UserProfiles")
    override suspend fun saveUser(oldName: String, profileData: User) {
        // We are storing only a single profile at a time, so use a unique document name to refer to it
        dbUserProfiles.document(oldName).set(profileData)
            .addOnSuccessListener {
                println("Profile saved.")
            }
            .addOnFailureListener { e ->
                println("Error saving profile: $e")
            }
    }

    override suspend fun delete(name: String) {
        dbUserProfiles.document(name)
            .delete()
            .addOnSuccessListener { println("Profile $name successfully deleted!") }
            .addOnFailureListener { error -> println("Error deleting profile $name: $error") }
    }


    override suspend fun getUser(email: String): Flow<User> = callbackFlow {

        val docRef = dbUserProfiles.document(email)
        val subscription = docRef.addSnapshotListener { snapshot, error ->
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
                    trySend(User("")) // If there is no saved profile, then send a default object
                }
            } else {
                // The user document does not exist or has no data
                println("Profile does not exist")
                trySend(User("")) // send default object
            }
        }
        awaitClose { subscription.remove() }
    }

    override suspend fun getUsers(): Flow<List<User>> = callbackFlow {

        // Listen for changes on entire collection
        val subscription = dbUserProfiles.addSnapshotListener { snapshot, error ->
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
