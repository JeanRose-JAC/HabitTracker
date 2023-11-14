package com.example.habittrackerapp.habit.database

import com.example.habittrackerapp.model.Habit
import com.example.habittrackerapp.model.HabitRepository
import kotlinx.coroutines.flow.Flow

class OfflineHabitsRepository (private val habitDao: HabitDao) : HabitRepository {
    override fun getAllHabitsStream(): Flow<List<Habit>> = habitDao.getAllItems()

    override fun getHabitStream(id: Int): Flow<Habit?> = habitDao.getItem(id)

    override suspend fun insertHabit(item: Habit) = habitDao.insert(item)

    override suspend fun deleteHabit(item: Habit) = habitDao.delete(item)

    override suspend fun updateHabit(item: Habit) = habitDao.update(item)
}