package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TakenTryOutModel(
    val details: String? = null,
    @SerialName("taken_details") val takenDetails: TakenDetails? = null,
    @SerialName("to_details") val tryoutDetails: ToDetails? = null
)