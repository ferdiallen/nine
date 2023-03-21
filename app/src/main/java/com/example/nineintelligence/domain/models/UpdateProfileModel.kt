package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileModel(
    @SerialName("user_name") val userName: String,
    @SerialName("user_email") val userEmail: String,
    @SerialName("password") val password: String,
    @SerialName("phone") val phone: String,
    @SerialName("address") val address: String,
    @SerialName("pp_link") val profilePicture: String,
    @SerialName("gender") val gender: String,
)