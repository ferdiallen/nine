package com.example.nineintelligence.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
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
        const val SAVED_USERNAME = "username"
        const val SAVED_PASSWORD = "password"
        val AUTH_KEY_PREF = stringPreferencesKey(AUTH_KEY)
        val USER_PREF = stringPreferencesKey(SAVED_USERNAME)
        val PASSWORD_PREF = stringPreferencesKey(SAVED_PASSWORD)
    }

    suspend fun saveToken(key: String) {
        store.edit { authKey ->
            authKey[AUTH_KEY_PREF] = key
        }
    }

    suspend fun rememberUser(name: String, password: String) {
        store.edit { userPrefs ->
            userPrefs[USER_PREF] = name
            userPrefs[PASSWORD_PREF] = password
        }
    }

    fun readToken(): String? = runBlocking {
        return@runBlocking store.data.map {
            it[AUTH_KEY_PREF]
        }.first()
    }

    suspend fun readTokenNonBlocking(): String? {
        return store.data.map {
            it[AUTH_KEY_PREF]
        }.first()
    }

    suspend fun isRememberSaved(userName: (String) -> Unit, password: (String) -> Unit): Boolean {
        val savedUser = store.data.map {
            listOf(
                it[USER_PREF],
                it[PASSWORD_PREF]
            ).takeWhile { out ->
                out != null
            }
        }.first()
        if (savedUser.isNotEmpty()) {
            userName(savedUser[0] ?: "")
            password(savedUser[1] ?: "")
        }
        return savedUser.isNotEmpty()
    }

    suspend fun clearData() {
        store.edit {
            it.remove(AUTH_KEY_PREF)
        }
    }

    suspend fun deleteSavedUser() {
        store.edit {
            it.remove(USER_PREF)
            it.remove(PASSWORD_PREF)
        }
    }
}