package com.hanisoft.bestar.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BeStarDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("beStar")

        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val USER_UNIQUE_ID = stringPreferencesKey("uniqueId")
        val BACK_FROM_TIKTOK = booleanPreferencesKey("backToScreen")
        val OPEN_DIALOG = booleanPreferencesKey("openDialog")
        val OPEN_LOGIN = booleanPreferencesKey("openLogin")
    }

    val getAuthToken: Flow<String?> = context.dataStore.data
        .map{ preferences ->
            preferences[AUTH_TOKEN] ?: ""
        }


    suspend fun savedAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }


    val getUserUniqueId: Flow<String?> = context.dataStore.data
        .map{ preferences ->
            preferences[USER_UNIQUE_ID] ?: ""
        }

    suspend fun saveUniqueId(uniqueId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_UNIQUE_ID] = uniqueId
        }
    }

    val getBackFomTikTokValue: Flow<Boolean?> = context.dataStore.data
        .map{ preferences ->
            preferences[BACK_FROM_TIKTOK] ?: false
        }

    suspend fun saveBackFomTikTokValue(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[BACK_FROM_TIKTOK] = value
        }
    }

val getOpenDialogValue: Flow<Boolean?> = context.dataStore.data
        .map{ preferences ->
            preferences[OPEN_DIALOG] ?: false
        }

    suspend fun saveOpenDialogalue(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[OPEN_DIALOG] = value
        }
    }


val getOpenLoginValue: Flow<Boolean?> = context.dataStore.data
        .map{ preferences ->
            preferences[OPEN_LOGIN] ?: false
        }

    suspend fun saveOpenLoginValue(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[OPEN_LOGIN] = value
        }
    }


}