package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussModel(
    val details: String? = null,
    val score: Int? = null,
    @SerialName("soal_detail") val soalDetail: SoalDetail ? = null,
    @SerialName("user_ans") val userAnswer: String? = null
)