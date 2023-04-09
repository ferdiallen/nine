package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BankSoalExamModel(
    @SerialName("soal_id") val soalId: Int? = null,
    val type: Int? = null,
    val mapel: Int? = null,
    val content: String? = null,
    val answers: List<String>? = emptyList()
)
