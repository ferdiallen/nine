package com.example.nineintelligence.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AuthPrefs(
    private val store: DataStore<Preferences>
) {

    private companion object {
        const val AUTH_KEY = "auth_token"
        const val TIMER_KEY = "token_time"
        val AUTH_KEY_PREF = stringPreferencesKey(AUTH_KEY)
        val TIMER_KEY_PREF = longPreferencesKey(TIMER_KEY)
    }

    suspend fun saveToken(key: String, definedTime: Long) {
        store.edit { authKey ->
            authKey[AUTH_KEY_PREF] = key
            authKey[TIMER_KEY_PREF] = definedTime
        }
    }

    fun readToken(): String? = runBlocking {
        return@runBlocking store.data.map {
            it[AUTH_KEY_PREF]
        }.first()
    }

    suspend fun readTime(): Long? {
        return store.data.map {
            it[TIMER_KEY_PREF]
        }.first()
    }

    suspend fun clearData() {
        store.edit {
            it.clear()
        }
    }
}