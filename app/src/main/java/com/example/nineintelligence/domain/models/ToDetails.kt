package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToDetails(
    val createdAt: String? = null,
    val duration: Int? = null,
    val endsAt: String? = null,
    val published: Boolean?= null,
    val startsAt: String?= null,
    @SerialName("to_id") val tryOutId: Int?= null,
    @SerialName("to_slug") val tryOutSlug: String?= null,
    @SerialName("to_summary") val tryOutSummary: String?= null,
    @SerialName("to_title") val tryOutTitle: String?= null
)

@Serializable
data class BankSoalDetails(
    val createdAt: String? = null,
    val published: Boolean?= null,
    @SerialName("bs_id") val bankSoalId: Int?= null,
    @SerialName("bs_slug") val tryOutSlug: String?= null,
    @SerialName("bs_summary") val tryOutSummary: String?= null,
    @SerialName("bs_title") val tryOutTitle: String?= null
)