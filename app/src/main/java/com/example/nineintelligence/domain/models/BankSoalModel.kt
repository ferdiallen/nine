package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankSoalModel(
    @SerialName("bs_title") val bankSoalTitle: String? = null,
    @SerialName("bs_slug") val bankSoalSlug: String? = null,
    @SerialName("bs_summary") val summary: String? = null,
)
