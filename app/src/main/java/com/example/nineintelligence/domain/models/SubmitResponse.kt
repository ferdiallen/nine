package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubmitResponse(
    @SerialName("taken_id") val takenId:Int? = null,
)
