package com.example.nineintelligence.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AuthPrefs(
    private val store: DataStore<Preferences>
) {

    private companion object {
        const val AUTH_KEY = "auth_token"
        val AUTH_KEY_PREF = stringPreferencesKey(AUTH_KEY)
    }

    suspend fun saveToken(key: String) {
        store.edit { authKey ->
            authKey[AUTH_KEY_PREF] = key
        }
    }

    fun readToken(): String? = runBlocking {
        return@runBlocking store.data.map {
            it[AUTH_KEY_PREF]
        }.first()
    }

    suspend fun clearData() {
        store.edit {
            it.clear()
        }
    }
}