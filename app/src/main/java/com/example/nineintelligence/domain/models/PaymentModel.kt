package com.example.nineintelligence.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentModel(
   @SerialName("order_details") val orderDetails: OrderDetails? = null,
   @SerialName("customer_details") val customerDetails: CustomerDetails? = null,
   @SerialName("billing_address") val billingAddress: BillingAddress? = null,
   @SerialName("shipping_address") val shippingAddress: ShippingAddress? = null
)

@Serializable
data class OrderDetails(
    @SerialName("item_id") val itemId: Int? = null,
    @SerialName("price") val price: Int? = null,
    @SerialName("item_name") val itemName: String? = null,
)

@Serializable
data class CustomerDetails(
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("phone") val phone: String? = null,
)

@Serializable
data class BillingAddress(
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("postal_code") val postalCode: Int? = null
)

@Serializable
data class ShippingAddress(
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("postal_code") val postalCode: Int? = null
)

@Serializable
data class PaymentReceivedModel(
    val token: String? = null,
    @SerialName("redirect_url") val redirectUrl: String? = null
)