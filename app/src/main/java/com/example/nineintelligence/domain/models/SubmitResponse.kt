package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubmitResponse(
    @SerialName("taken_id") val takenId: Int? = null,
    @SerialName("hasil_id") val hasilId: Int? = null,
    val totalFalse: Int? = null,
    val totalCorrect: Int? = null,
    @SerialName("to_id") val tryOutId: Int? = null,
    @SerialName("user_id") val userId: String? = null,
    val score: Int? = null
)

@Serializable
data class BankSoalSubmitResponse(
    @SerialName("bs_id") val bankSoalId: Int? = null,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("bstalCorrect") val bankSoalTotalCorrectAnswer: Int? = null,
    val score: Int? = null,
    @SerialName("taken_id") val takenId: Int? = null,
    @SerialName("hasil_id") val hasilId: Int? = null,
    @SerialName("bstalFalse") val bankSoalTotalWrongAnswer: Int? = null

)