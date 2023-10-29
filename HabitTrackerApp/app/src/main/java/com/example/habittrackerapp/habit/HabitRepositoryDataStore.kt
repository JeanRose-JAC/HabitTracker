package com.example.habittrackerapp.habit

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.habittrackerapp.model.ProfileData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val HABIT_DATASTORE ="habit_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = HABIT_DATASTORE)

class HabitRepositoryDataStore (private val context: Context) : HabitRepository {

    companion object {
        val DESCRIPTION = stringPreferencesKey("DESCRIPTION")
        val STARTDATE = stringPreferencesKey("STARTDATE")
        val FREQUENCY = stringPreferencesKey("FREQUENCY")
        val TYPE = stringPreferencesKey("TYPE")
        val STREAK = intPreferencesKey("STREAK")
    }

    override suspend fun saveHabit (habitData: Habit) {
        context.dataStore.edit {
            it[DESCRIPTION] = habitData.description
            it[STARTDATE] = habitData.startDate
            it[FREQUENCY] = habitData.frequency
            it[TYPE] = habitData.type
            it[STREAK] = habitData.streak
        }
    }

    override fun getHabit (): Flow<Habit> = context.dataStore.data.map {
        Habit(
            description = it[DESCRIPTION] ?: "",
            startDate = it[STARTDATE] ?: "",
            frequency = it[FREQUENCY] ?: "",
            type = it[TYPE] ?: "",
            streak = it[STREAK] ?: 0
        )
    }

    override suspend fun clear () {
        context.dataStore.edit {
            it.clear()
        }
    }

}