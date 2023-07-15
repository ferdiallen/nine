package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.CreatePayment
import com.example.nineintelligence.domain.models.BillingAddress
import com.example.nineintelligence.domain.models.CustomerDetails
import com.example.nineintelligence.domain.models.OrderDetails
import com.example.nineintelligence.domain.models.PaymentItemModel
import com.example.nineintelligence.domain.models.PaymentModel
import com.example.nineintelligence.domain.models.PaymentReceivedModel
import com.example.nineintelligence.domain.models.ShippingAddress
import com.example.nineintelligence.domain.models.UserProfileModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CreatePaymentImpl(
    private val http: HttpClient,
    private val prefs: AuthPrefs
) : CreatePayment {
    override suspend fun createPayment(
        paymentInfo: PaymentItemModel,
        userData: UserProfileModel
    ): PaymentReceivedModel {
        return http.post(urlString = "${BuildConfig.BASE_URL}payment") {
            setBody(
                PaymentModel(
                    OrderDetails(paymentInfo.itemId, paymentInfo.price, paymentInfo.itemName),
                    CustomerDetails(userData.userName, "", userData.userEmail, ""),
                    BillingAddress(
                        userData.userName, "", userData.address ?: "",
                        "", 0
                    ),
                    ShippingAddress(
                        userData.userName, "", userData.address ?: "",
                        "", 0
                    )
                )
            )
            contentType(ContentType.Application.Json)
            bearerAuth(prefs.readTokenNonBlocking() ?: return@post)
        }.body()
    }
}