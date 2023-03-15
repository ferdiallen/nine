package com.example.nineintelligence.presentation.enter

import com.example.nineintelligence.domain.models.LoginModel

data class LoginState(
    val isLoading: Boolean = false,
    val tokenData: String? = "",
    val hasError: String = ""
)
