package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TakenDetails(
    @SerialName("finishAt") val finishAt: String? = null,
    @SerialName("status") val status: Int? = null,
    @SerialName("takenAt") val takenAt: String? = null,
    @SerialName("taken_id") val takenId: Int? = null,
    @SerialName("to_id") val bankSoalId: Int? = null,
    @SerialName("type") val type: Int? = null,
    @SerialName("user_id") val userId: String? = null
)

@Serializable
data class TakenBankSoalDetails(
    @SerialName("finishAt") val finishAt: String? = null,
    @SerialName("status") val status: Int? = null,
    @SerialName("takenAt") val takenAt: String? = null,
    @SerialName("taken_id") val takenId: Int? = null,
    @SerialName("bs_id") val bankSoalId: Int? = null,
    @SerialName("type") val type: Int? = null,
    @SerialName("user_id") val userId: String? = null
)