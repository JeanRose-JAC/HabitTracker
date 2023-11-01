package com.example.habittrackerapp.habit

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun saveHabit(habitData: Habit)
    fun getHabit(): Flow<Habit>
    suspend fun clear()
}