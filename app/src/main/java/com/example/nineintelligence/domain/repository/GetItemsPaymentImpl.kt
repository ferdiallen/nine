package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.GetItemsPayment
import com.example.nineintelligence.domain.models.PaymentItemModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GetItemsPaymentImpl(
    private val http: HttpClient,
    private val authPrefs: AuthPrefs
) : GetItemsPayment {
    override suspend fun getListItemsPayment(): List<PaymentItemModel> {
        return http.get("${BuildConfig.BASE_URL}payment/items") {
            contentType(ContentType.Application.Json)
            bearerAuth(authPrefs.readTokenNonBlocking() ?: return@get)
        }.body()
    }
}