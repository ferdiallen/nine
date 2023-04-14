package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubmitModel(
    @SerialName("user_answers") val userAnswers: List<UserAnswerData> = emptyList()
) {
    @Serializable
    data class UserAnswerData(
        @SerialName("soal_id") val id: Int? = null,
        @SerialName("answer") val answer: String? = null
    )
}
