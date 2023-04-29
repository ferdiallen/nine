package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartTryoutResponse(
    @SerialName("soal_struct") val soalStruct: String? = null,
    @SerialName("draft_id") val draftId: Int? = null,
    @SerialName("duration") val duration: Int? = null,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("to_id") val tryoutId: Int? = null,
    @SerialName("user_answers") val userAnswer: List<UserAnswerData> = emptyList()
)
