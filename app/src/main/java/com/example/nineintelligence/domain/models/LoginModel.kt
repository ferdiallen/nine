package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    @SerialName("token") val token: String ="",
    @SerialName("token_type") val tokenType: String =""
)
