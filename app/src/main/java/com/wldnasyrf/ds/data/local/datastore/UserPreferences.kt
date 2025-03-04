package com.wldnasyrf.ds.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.wldnasyrf.ds.data.local.datastore.model.UserPreferencesModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val TOKEN = stringPreferencesKey("user_token")

    fun getUserPreferences(): Flow<UserPreferencesModel> {
        return dataStore.data.map { preferences ->
            UserPreferencesModel(
                token = preferences[TOKEN] ?: ""
            )
        }
    }

    suspend fun saveUserPreferences(data: UserPreferencesModel) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = data.token
        }
    }

    suspend fun clearUserPreferences(){
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}