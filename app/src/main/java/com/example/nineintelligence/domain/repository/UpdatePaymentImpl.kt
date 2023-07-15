package com.example.nineintelligence.domain.repository

import com.example.nineintelligence.BuildConfig
import com.example.nineintelligence.core.AuthPrefs
import com.example.nineintelligence.data.network.apiservice.UpdatePayment
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.put

class UpdatePaymentImpl(
    private val http: HttpClient,
    private val prefs: AuthPrefs
) : UpdatePayment {
    override suspend fun updatePayment(orderid: String): String {
        return http.put(urlString = "${BuildConfig.BASE_URL}payment/update/$orderid") {
            bearerAuth(prefs.readTokenNonBlocking() ?: return@put)
        }.body()
    }
}