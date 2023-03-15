package com.example.nineintelligence.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nineintelligence.core.AuthPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val store: AuthPrefs
    ) : ViewModel() {
    private val _hasLoggedIn = MutableStateFlow(false)
    val hasLoggedIn = _hasLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            val logInAuthKey = store.readToken()
            if (logInAuthKey != null) {
                _hasLoggedIn.update {
                    true
                }
            }
        }
    }
}