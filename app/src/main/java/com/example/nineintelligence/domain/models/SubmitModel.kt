package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubmitModel(
    @SerialName("user_answer") val userAnswers: List<String?> = emptyList()
)
