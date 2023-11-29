package com.example.habittrackerapp.model.noteViewModel

import com.example.habittrackerapp.auth.AuthRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NoteRepositoryFirestore (val auth: AuthRepository, val db: FirebaseFirestore) :
    NoteRepository {
    val dbNote: CollectionReference = db.collection("Notes")
    override suspend fun saveNote(id: String, note: Note) {
        if (auth.hasCurrentUserDirect()) {
            // We are storing only a single note at a time, so use a unique document name to refer to it
            dbNote.document(id).set(note)
                .addOnSuccessListener {
                    println("Note saved.")
                }
                .addOnFailureListener { e ->
                    println("Error saving note: $e")
                }
        } else {
            println("Save Note failed: User is not authenticated")
        }
    }

    override suspend fun getNotes(): Flow<List<Note>> = callbackFlow{
        // Listen for changes on entire collection
        val subscription = dbNote.addSnapshotListener{ snapshot, error ->
            if (error != null) {
                // An error occurred
                println("Listen failed: $error")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                // The collection has documents, so convert them all to Note objects
                val note = snapshot.toObjects(Note::class.java)
                if (note != null) {
                    println("Real-time update to note")
                    trySend(note)
                } else {
                    println("Notes has become null")
                    trySend(listOf<Note>()) // If there is no saved note, then send a default object
                }
            } else {
                // The note document does not exist or has no data
                println("Note collection does not exist")
                trySend(listOf<Note>()) // send default object
            }
        }
        awaitClose { subscription.remove() }
    }

    override suspend fun getNote(id: String): Flow<Note> = callbackFlow {
        val docRef = dbNote.document(id)
        val subscription = docRef.addSnapshotListener{ snapshot, error ->
            if (error != null) {
                // An error occurred
                println("Listen failed: $error")
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                // The note document has data
                val note = snapshot.toObject(Note::class.java)
                if (note != null) {
                    println("Real-time update to note")
                    trySend(note)
                } else {
                    println("Note is / has become null")
                    trySend(Note()) // If there is no saved note, then send a default object
                }
            } else {
                // The note document does not exist or has no data
                println("Note does not exist")
                trySend(Note()) // send default object
            }
        }
        awaitClose { subscription.remove() }
    }

    override suspend fun deleteNote(id: String) {
        if (auth.hasCurrentUserDirect()) {
            dbNote.document(id)
                .delete()
                .addOnSuccessListener { println("Note successfully deleted!") }
                .addOnFailureListener { error -> println("Error deleting note") }
        } else {
            println("Delete failed: User is not authenticated")
        }
    }

}
