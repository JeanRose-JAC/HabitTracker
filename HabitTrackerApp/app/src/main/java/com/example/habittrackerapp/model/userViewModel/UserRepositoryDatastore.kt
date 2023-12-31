package com.example.habittrackerapp.model.userViewModel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val SAVED_USER_DATASTORE ="saved_user_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SAVED_USER_DATASTORE)


/**
 * this implements the SaveUserRepository to kae a datastore and allow the user to stay signIn if they stop running
 * the application
 * @param context it takes in the application context to let the phone remember the user information
 */
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
            email = it[EMAIL] ?: "empty",
            password = it[PASSWORD] ?: "empty"
        )
    }

    override suspend fun clear() {
        context.dataStore.edit {
            it.clear()
        }
    }
}