package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    @SerialName("user_name") val userName: String,
    @SerialName("user_email") val userEmail: String,
    @SerialName("password") val password: String,
    @SerialName("phone") val phoneNumber: String

)

@Serializable
data class UserProfileModel(
    @SerialName("user_id") val userId: String = "",
    @SerialName("user_name") val userName: String = "",
    @SerialName("user_email") val userEmail: String = "",
    @SerialName("address") val address: String? = null,
    @SerialName("gender") val gender: String? = null
)