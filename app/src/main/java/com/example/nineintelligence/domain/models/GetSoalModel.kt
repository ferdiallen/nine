package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSoalModel(
    val answers: List<String?>? = emptyList(),
    val content: String? = null,
    val mapel: Int? = null,
   @SerialName("soal_id") val idSoal: Int? = null,
    val type: Int? = null
)