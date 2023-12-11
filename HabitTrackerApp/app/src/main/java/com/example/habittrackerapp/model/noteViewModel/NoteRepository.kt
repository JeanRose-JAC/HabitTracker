package com.example.habittrackerapp.model.noteViewModel

import com.example.habittrackerapp.model.noteViewModel.Note
import kotlinx.coroutines.flow.Flow

/**
 * Notes repo that defines the operations that can be done on the habit data
 */
interface NoteRepository {
    /**
     * Update and Saves item in the data source
     */
    suspend fun saveNote(id: String, note: Note)
    /**
     * Retrieve all the items from the the given data source.
     */
    suspend fun getNotes(): Flow<List<Note>>
    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    suspend fun getNote(id: String): Flow<Note>
    /**
     * Delete item from the data source
     */
    suspend fun deleteNote(id: String)
}