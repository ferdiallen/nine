package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TryoutDataModel(
    @SerialName("to_title") val title: String,
    @SerialName("to_slug") val slugName: String,
    @SerialName("to_summary") val summary: String,
    @SerialName("startsAt") val startTime: String,
    @SerialName("endsAt") val endTime: String
)
