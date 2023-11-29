package com.example.habittrackerapp.habit.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habittrackerapp.model.habitViewModel.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Habit)

    @Update
    suspend fun update(item: Habit)

    @Delete
    suspend fun delete(item: Habit)

    @Query("SELECT * from habits WHERE id = :id")
    fun getItem(id: Int): Flow<Habit>

    @Query("SELECT * from habits ORDER BY description ASC")
    fun getAllItems(): Flow<List<Habit>>
}