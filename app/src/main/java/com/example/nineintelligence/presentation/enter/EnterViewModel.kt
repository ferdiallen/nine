package com.example.nineintelligence.presentation.enter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class EnterViewModel : ViewModel() {
    private val _currentEmail = MutableStateFlow("")
    private val _currentPassword = MutableStateFlow("")
    private val _isCheckedRememberMe = MutableStateFlow(false)

    val currentEmail = _currentEmail.asStateFlow()
    val currentPassword = _currentPassword.asStateFlow()
    val isCheckedRememberMe = _isCheckedRememberMe.asStateFlow()

    fun onEmailChange(text: String) = _currentEmail.update { text }
    fun onPasswordChange(text: String) = _currentPassword.update { text }
    fun onCheckedChange(data: Boolean) = _isCheckedRememberMe.update { data }
}