package com.example.habittrackerapp.model

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllHabitsStream(): Flow<List<Habit>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getHabitStream(id: Int): Flow<Habit?>

    /**
     * Insert item in the data source
     */
    suspend fun insertHabit(item: Habit)

    /**
     * Delete item from the data source
     */
    suspend fun deleteHabit(item: Habit)

    /**
     * Update item in the data source
     */
    suspend fun updateHabit(item: Habit)
}