package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TakeTryOutModel(
    @SerialName("type") val type: Int? = null
)
