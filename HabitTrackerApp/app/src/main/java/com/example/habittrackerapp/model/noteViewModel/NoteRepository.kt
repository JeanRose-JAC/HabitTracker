package com.example.habittrackerapp.model.noteViewModel

import com.example.habittrackerapp.model.noteViewModel.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun saveNote(id: String, note: Note)
    suspend fun getNotes(): Flow<List<Note>>
    suspend fun getNote(id: String): Flow<Note>
    suspend fun deleteNote(id: String)
}