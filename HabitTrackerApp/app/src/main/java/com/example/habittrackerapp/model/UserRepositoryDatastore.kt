package com.example.habittrackerapp.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val SAVED_USER_DATASTORE ="saved_user_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SAVED_USER_DATASTORE)

class UserRepositoryDatastore(private val context: Context) : SavedUserRepository {
    companion object {
        val EMAIL = stringPreferencesKey("EMAIL")
        val PASSWORD = stringPreferencesKey("PASSWORD")
    }
    override suspend fun saveProfile(user: SavedUser) {
        context.dataStore.edit {
            it[EMAIL] = user.email
            it[PASSWORD] = user.password
        }
    }

    override fun getProfile(): Flow<SavedUser> = context.dataStore.data.map {
        SavedUser(
            email = it[EMAIL] ?: "",
            password = it[PASSWORD] ?: ""
        )
    }

    override suspend fun clear() {
        context.dataStore.edit {
            it.clear()
        }
    }
}