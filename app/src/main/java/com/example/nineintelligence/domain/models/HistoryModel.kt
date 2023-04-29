package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryModel(
    @SerialName("to_hasil") val hasilTryout: SubmitResponse? = null,
    @SerialName("to_details") val tryoutDetails: ToDetails? = null
)
