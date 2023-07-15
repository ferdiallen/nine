package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestPaymentResponse(
    @SerialName("order_id") val orderId: String? = null,
    @SerialName("payment_id") val paymentId: Int? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("customer_details") val customerDetails: CustomerDetails? = null,
    @SerialName("shipping_address") val shippingAddress: ShippingAddress? = null,
    @SerialName("order_details") val orderDetails: OrderDetails? = null,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("billing_address") val billingAddress: BillingAddress? = null,
    @SerialName("createdAt") val createdAt: String? = null
)
