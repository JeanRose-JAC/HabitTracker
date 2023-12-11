package com.example.habittrackerapp.model.habitViewModel

import com.example.habittrackerapp.model.habitViewModel.Habit
import kotlinx.coroutines.flow.Flow

/**
 * Habit repo that defines the operations that can be done on the habit data
 */
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