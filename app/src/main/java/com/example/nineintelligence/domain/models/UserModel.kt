package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    @SerialName("user_name") val userName: String,
    @SerialName("user_email") val userEmail: String,
    @SerialName("password") val password: String
)

@Serializable
data class UserProfileModel(
    @SerialName("user_id") val userId: String = "",
    @SerialName("user_name") val userName: String = "",
    @SerialName("user_email") val userEmail: String = ""
)