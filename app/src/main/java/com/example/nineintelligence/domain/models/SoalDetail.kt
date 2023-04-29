package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SoalDetail(
    val answers: List<String> = emptyList(),
    val content: String? = null,
    val correctAns: String? = null,
    val createdAt: String? = null,
    val image_container: String? = null,
    val mapel: Int? = null,
    @SerialName("soal_id") val soalId: Int? = null,
    @SerialName("to_id") val tryoutId: Int? = null,
    val type: Int? = null,
    val updatedAt: String? = null
)