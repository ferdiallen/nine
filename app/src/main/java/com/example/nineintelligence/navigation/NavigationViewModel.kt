package com.example.nineintelligence.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.core.WorkerTimer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val store: AuthPrefs,
    private val worker: WorkManager
) : ViewModel() {
    private val _hasLoggedIn = MutableStateFlow(false)
    val hasLoggedIn = _hasLoggedIn.asStateFlow()
    private val authOneTime = OneTimeWorkRequestBuilder<WorkerTimer>().build()

    init {
        worker.beginUniqueWork(
            "auth_authentication",
            ExistingWorkPolicy.KEEP,
            authOneTime
        ).enqueue()
        viewModelScope.launch {
            val logInAuthKey = store.readToken()
            if (logInAuthKey != "" && logInAuthKey != null) {
                _hasLoggedIn.update {
                    true
                }
            }
        }
    }
}