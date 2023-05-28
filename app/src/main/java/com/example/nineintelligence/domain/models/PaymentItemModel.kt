package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentItemModel(
    val price: Int? = null,
    @SerialName("item_id") val itemId: Int? = null,
    @SerialName("item_name") val itemName: String? = null,
    val createdAt: String? = null
)
