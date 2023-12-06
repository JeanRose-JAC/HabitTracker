package com.example.habittrackerapp.habit.database

import com.example.habittrackerapp.model.habitViewModel.Habit
import com.example.habittrackerapp.model.habitViewModel.HabitRepository
import kotlinx.coroutines.flow.Flow

/**
 * Defines the repo for the habits table
 */
class OfflineHabitsRepository (private val habitDao: HabitDao) : HabitRepository {
    override fun getAllHabitsStream(): Flow<List<Habit>> = habitDao.getAllItems()

    override fun getHabitStream(id: Int): Flow<Habit?> = habitDao.getItem(id)

    override suspend fun insertHabit(item: Habit) = habitDao.insert(item)

    override suspend fun deleteHabit(item: Habit) = habitDao.delete(item)

    override suspend fun updateHabit(item: Habit) = habitDao.update(item)
}